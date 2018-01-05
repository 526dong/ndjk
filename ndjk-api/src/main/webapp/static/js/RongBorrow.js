/**
 * Created by Administrator on 2017/7/17 0017.
 */
//    1. 初始化
window.onload = function(){
    //    1.获取页面的链接里的参数 - 进行解析
    var objdata = {
        protocolid:"",
        userid:"",
        borrowId:"",
        username:"",
        usercardid:"",
        usertell:"",
        loanTime:"",
        repayTime:"",
        alldays:"",
        money:"",
        RongID:""
    };
   //获取url中的信息
    var urlhref  = location.href;
    ResolveURL(urlhref,objdata);

    function ResolveURL(url,obj){
        if(typeof obj == "undefined") return;
        if(url.indexOf("?") < 0 ) return;
        var strurl =  location.search;
        var tempobj = strurl.substring(1);
        var sarr = tempobj.split("&");
        for(var i=0; i<sarr.length; i++){
            var temparr = sarr[i].split("=");
            if(temparr[0] == "userid"){
                obj.userid = temparr[1];
            }else if(temparr[0] == "borrowId"){
                obj.borrowId = temparr[1];
            }else if(temparr[0]=='RongID'){
                obj.RongID = temparr[1];
            }
        }
        return obj;
    }

//  请求ajax，获取名字，身份证号,合同编号
    function RequestUser(obj){
        if(obj.userid == "" ) return;
        if(obj.borrowId == "" ) return;
        $.ajax({
            url:'/api/protocol/borrow.htm?userId='+ obj.userid+"&borrowId="+obj.borrowId,
            type:'GET',
            success:function(rep){
                var userData =(typeof rep.data) == "undefined"?{}:rep.data;
                if(typeof userData == "object"){
                    obj.usercardid =(typeof userData.idNo) == "undefined"? " ": userData.idNo;//身份证号
                    obj.protocolid = (typeof userData.protocol) == "undefined"? " ":userData.protocol;//协议码
                    obj.username = (typeof userData.realName) == "undefined"? " ":userData.realName;//姓名
                    obj.usertell =(typeof userData.phone) == "undefined"? " ": userData.phone;//电话

                    obj.loanTime =(typeof userData.loanTime) == "undefined"? " ": userData.loanTime;//借款时间
                    obj.repayTime = (typeof userData.repayTime) == "undefined"? " ":userData.repayTime;//还款时间
                    obj.alldays = (typeof userData.timeLimit) == "undefined"? " " :userData.timeLimit;//借款期限
                    obj.money = (typeof userData.amount) == "undefined"? " " :userData.amount;//借款金钱
                    setData(obj)
                }
            },
            error:function(){
                console.log('error');
            }
        })
    }
//  函数- 进行数字转化汉字
    function TranstionTXT(money) {
        if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(money)) return "数据非法";
        var unit = "京亿万仟佰拾兆万仟佰拾亿仟佰拾万仟佰拾元角分", str = "";
        money += "00";
        var p = money.indexOf('.');
        if (p >= 0)
            money = money.substring(0, p) + money.substr(p+1, 2);
        unit = unit.substr(unit.length - money.length);
        for (var i=0; i < money.length; i++) str += '零壹贰叁肆伍陆柒捌玖'.charAt(money.charAt(i)) + unit.charAt(i);
        return str.replace(/零(仟|佰|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(兆|万|亿|元)/g, "$1").replace(/(兆|亿)万/g, "$1").replace(/(京|兆)亿/g, "$1").replace(/(京)兆/g, "$1").replace(/(京|兆|亿|仟|佰|拾)(万?)(.)仟/g, "$1$2零$3仟").replace(/^元零?|零分/g, "").replace(/(元|角)$/g, "$1整");
    }

    function setData(obj){
        document.getElementById("protocalID").innerHTML = obj.protocolid;
        document.getElementById("RongID").innerHTML =obj.RongID;
        document.getElementById("userName").innerText = obj.username;
        document.getElementById("userID").innerHTML = obj.usercardid;
        document.getElementById("userTel").innerHTML =obj.usertell;
        document.getElementById("Money").innerHTML = objdata.money;
        document.getElementById("Allday").innerHTML =objdata.alldays;

        document.getElementById("MoneyT").innerHTML = TranstionTXT(objdata.money);
        var startArr = objdata.loanTime.substring(0,10).split("-");
        if(startArr.length == 0)  startArr=[" "," "," "];
        document.getElementById("Syear").innerHTML =  startArr[0];
        document.getElementById("Smon").innerHTML =startArr[1];
        document.getElementById("Sday").innerHTML =startArr[2];

        var endArr = objdata.repayTime.substring(0,10).split("-");
        if(endArr.length == 0)  endArr=[" "," "," "];
        document.getElementById("Eyear").innerHTML =endArr[0];
        document.getElementById("Emon").innerHTML =endArr[1];
        document.getElementById("Eday").innerHTML =endArr[2];

    }

    RequestUser(objdata);





}
