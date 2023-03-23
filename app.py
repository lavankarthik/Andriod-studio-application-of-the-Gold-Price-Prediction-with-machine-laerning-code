
# 1. Library imports
import uvicorn
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
import pickle


# 2. Create the app object
app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# 4. Index route, opens automatically on http://127.0.0.1:8000
@app.get('/')
def index():
    return {'message': 'Hello World'}

@app.get("/predictGLD")
def getPredictGLD(SPX: float, USO	: float,SLV: float,CUR: float):
    rgModel = pickle.load(open("rgr.pkl", "rb"))
    
    prediction = rgModel.predict([[SPX,USO,SLV,CUR]])
    
    return {
        'GLD': prediction[0]
    }

if __name__ == '__main__':
    uvicorn.run(app, port=8080, host='0.0.0.0')
#http://127.0.0.1/predictGLD?SPX=78.470001&USO=78.470001&SLV=15.1800&CUR=1.471692
#uvicorn app:app --reload