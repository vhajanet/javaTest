from flask import Flask, request, render_template
import dataGo


app = Flask(__name__)



@app.route("/pharm/<idx>")
def pharmacy(idx):
    i = int(idx)-1
    print("전체데이터수:", len(rList))
    print("인덱스:", i)
    item = rList[i]
    return render_template("pharmacy.html",item=item)

@app.route("/pharmList")
def pharmacyList():
    global rList
    rList = dataGo.getPharmList()
    # for row in r:
    #     rList.append(row)
    return render_template("pharmList.html", list=rList)

@app.route("/serach")
def search():
    return  "hello"



if __name__ == "__main__":
    app.run(host='203.236.209.91', debug=True)