#18쪽

import requests
import bs4

endpoint = 'http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?'
serviceKey = 'bLc9knuvnpFo6%2BgpIjedk7%2BgG8EQsI32JWmXJXb9F%2Bu%2B1HxorGbVbRPDYsDZHcLRwxY1uEC6CyXO6NoEDT89Tw%3D%3D'
numOfRows="10"
pageSize="1"
pageNo="1"
MobileOS="ETC"
MobileApp="AppTest"
arrange = "A"
contentTypeId="15"
areaCode="1"
sigunguCode="1"
listYN="Y"
paramset = "serviceKey="+serviceKey+"&numOfRows="+numOfRows+"&pageSize="+pageSize+"&pageNo="+pageNo+"&MobileOS="+MobileOS+"&MobileApp="+MobileApp+"&arrange="+arrange+"&contentTypeId="+contentTypeId+"&areaCode="+areaCode+"&sigunguCode="+sigunguCode+"&listYN="+listYN

url = endpoint+paramset
print(url)
result = requests.get(url)
bs_obj = bs4.BeautifulSoup(result.content, 'html.parser')
title_list = bs_obj.findAll("title")
print(title_list)

#112










