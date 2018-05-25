from flask import Flask, send_from_directory, request, redirect, url_for
from werkzeug.utils import secure_filename
import os

cwd = os.getcwd()
uploadPresentation = 'presentation'
uploadRecipe = 'recipe'
uploadImage = 'image'
uploadVideo = 'video'
uploadAudio = 'audio'
UPLOAD_PRESENTATION = os.path.join(cwd, uploadPresentation)
UPLOAD_RECIPE = os.path.join(cwd, uploadRecipe)
UPLOAD_IMAGE = os.path.join(cwd, uploadImage)
UPLOAD_VIDEO = os.path.join(cwd, uploadVideo)
UPLOAD_AUDIO = os.path.join(cwd, uploadAudio)

ALLOWED_EXTENSIONS = set(['xml', 'pws', 'jpg', 'png', 'jpeg'])

app = Flask(__name__)
app.config['UPLOAD_PRESENTATION'] = UPLOAD_PRESENTATION
app.config['UPLOAD_RECIPE'] = UPLOAD_RECIPE
app.config['UPLOAD_IMAGE'] = UPLOAD_IMAGE
app.config['UPLOAD_VIDEO'] = UPLOAD_VIDEO
app.config['UPLOAD_AUDIO'] = UPLOAD_AUDIO

@app.route('/download/presentation/<id>')
def fetchPresentation(id):
    return send_from_directory(UPLOAD_PRESENTATION, "{}.pws".format(id))

@app.route('/download/recipe/<id>')
def fetchRecipe(id):
    return send_from_directory(UPLOAD_RECIPE, "{}.xml".format(id))

@app.route('/download/image/<id>')
def fetchImage(id):
    return send_from_directory(UPLOAD_IMAGE, "{}".format(id))

@app.route('/download/video/<id>')
def fetchVideo(id):
    return send_from_directory(UPLOAD_VIDEO, "{}".format(id))

@app.route('/download/audio/<id>')
def fetchAudio(id):
    return send_from_directory(UPLOAD_AUDIO, "{}".format(id))

def allowed_file(filename):
    return '.' in filename and \
        filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

@app.route('/recipelist')
def fetchRecipes():
    return "\n".join(next(os.walk(UPLOAD_RECIPE))[2])

@app.route('/upload/presentation', methods=['GET', 'POST'])
def storePresentation():
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
            file.save(os.path.join(app.config['UPLOAD_PRESENTATION'], filename))
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

@app.route('/upload/recipe', methods=['GET', 'POST'])
def storeRecipe():
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
            file.save(os.path.join(app.config['UPLOAD_RECIPE'], filename))
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
