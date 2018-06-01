import glob
import os

from flask import Flask, send_from_directory, request, redirect, safe_join
from werkzeug.utils import secure_filename

cwd = os.getcwd()
uploadPresentation = 'presentation'
uploadRecipe = 'recipe'
uploadMyRecipe = 'myRecipe'
UPLOAD_PRESENTATION = os.path.join(cwd, uploadPresentation)
UPLOAD_RECIPE = os.path.join(cwd, uploadRecipe)
UPLOAD_MY_RECIPE = os.path.join(cwd, uploadMyRecipe)

ALLOWED_EXTENSIONS = set(['xml', 'pws', 'jpg', 'png', 'jpeg'])

app = Flask(__name__)
app.config['UPLOAD_PRESENTATION'] = UPLOAD_PRESENTATION
app.config['UPLOAD_RECIPE'] = UPLOAD_RECIPE
app.config['UPLOAD_MY_RECIPE'] = UPLOAD_MY_RECIPE

@app.route('/download/recipe/<fileid>/<path:mediaid>')
def fetchRecipeMedia(fileid, mediaid):
    print "fetchRecipeMedia"
    return send_from_directory(UPLOAD_RECIPE, safe_join(fileid, mediaid))

@app.route('/download/myRecipe/<fileid>/<path:mediaid>')
def fetchMyRecipeMedia(fileid, mediaid):
    print "fetchRecipeMedia"
    return send_from_directory(UPLOAD_MY_RECIPE, safe_join(fileid, mediaid))

def allowed_file(filename):
    return '.' in filename and \
        filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

@app.route('/recipelist')
def fetchRecipes():
    return "\n".join(os.path.split(x)[1] for x in glob.glob("recipe/*/*.xml"))
    # return "\n".join(next(os.walk(UPLOAD_RECIPE))[2])
	
@app.route('/myRecipeList')
def fetchMyRecipes():
    return "\n".join(os.path.split(x)[1] for x in glob.glob("myRecipe/*/*.xml"))

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
