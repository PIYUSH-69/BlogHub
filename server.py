from flask import Flask, request, jsonify
import numpy as np
import tensorflow as tf
from transformers import AutoTokenizer

# Initialize Flask app
app = Flask(__name__)

# Load the tokenizer and model
tokenizer = AutoTokenizer.from_pretrained("bert-base-uncased")

# Load the TFLite model
model_path = 'C:/Users/ADMIN/Desktop/bert/blogfinal2 (1).tflite'  # Change this to your model path
interpreter = tf.lite.Interpreter(model_path=model_path)
interpreter.allocate_tensors()

# Get input and output tensors
input_details = interpreter.get_input_details()
output_details = interpreter.get_output_details()



@app.route('/')
def home():
    return "Welcome to the TensorFlow Lite model server, PIYUSH DALVI! "

# Prediction route
@app.route('/predict', methods=['POST'])
def predict():
    # Get input data from request
    data = request.json
    text = data.get('text', '')

    if not text:
        return jsonify({'error': 'Text is required'}), 400

    # Tokenize the input text
    inputs = tokenizer(text, padding='max_length', max_length=512, truncation=True, return_tensors='np')

    # Extract the input tensors (convert to int32 as required by TFLite)
    input_ids = inputs['input_ids'].astype(np.int32)
    attention_mask = inputs['attention_mask'].astype(np.int32)
    token_type_ids = inputs['token_type_ids'].astype(np.int32)

    # Set tensors for the interpreter
    interpreter.set_tensor(input_details[0]['index'], attention_mask)
    interpreter.set_tensor(input_details[1]['index'], input_ids)
    interpreter.set_tensor(input_details[2]['index'], token_type_ids)

    # Run inference
    interpreter.invoke()

    # Get the output and predicted class
    output = interpreter.get_tensor(output_details[0]['index'])
    predicted_class = np.argmax(output)

    # Return prediction as JSON response
    return jsonify({
        'predicted_class': int(predicted_class),
        'output': output.tolist()
    })

# Run the Flask app
if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
