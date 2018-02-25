from flask import Flask, send_from_directory, request, redirect, url_for
from werkzeug.utils import secure_filename
import os

UPLOAD_FOLDER = '/Users/Jack/AndroidStudioProjects/testospring/app/src/main/python/upload'
ALLOWED_EXTENSIONS = set(['xml', 'pws'])

app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

@app.route('/download/<id>')
def fetchXML(id):
    return send_from_directory(UPLOAD_FOLDER, "{}".format(id))

def allowed_file(filename):
    return '.' in filename and \
        filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

@app.route('/upload', methods=['GET', 'POST'])
def storeXML():
    if request.method == 'POST':
        # check if the post request has the file part
        if 'file' not in request.files:
            flash('No file part')
            return redirect(request.url)
        file = request.files['file']
        # if user does not select file, browser also
        # submit a empty part without filename
        if file.filename == '':
            flash('No selected file')
            return redirect(request.url)
        if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))
            return "KEWL FILE UPLOADED BRAH"
                #return redirect(url_for('uploaded_file',
#                    filename=filename))
    return '''
        <!doctype html>
        <title>Upload new File</title>
        <h1>Upload new File</h1>
        <form method=post enctype=multipart/form-data>
        <p><input type=file name=file>
        <input type=submit value=Upload>
        </form>
        '''
