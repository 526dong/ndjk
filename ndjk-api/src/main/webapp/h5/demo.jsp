<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <title>LianLianPay Wap Demo</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <style>
        .info {
            padding: 15px;
        }

        .table_ui {
            width: 100%;
            margin: 0 auto;
        }

        .table_ui td {
            line-height: 1.5em;
            padding-bottom: 10px;
            vertical-align: top;
        }

        .ft_gray {
            color: #999;
        }

        .slogan {
            overflow: hidden;
            width: 100%;
            height: 19px;
            position: relative;
            margin: 20px 0 5px 0;
        }

        .slogan h3 {
            font-size: 18px;
            line-height: 19px;
            padding-left: 1%;
            color: #4d4d4d;
            position: absolute;
            background: #f2f2f2;
            z-index: 100;
            padding: 0 0.215em;
            font-weight: normal;
            font-family: "微软雅黑";
        }

        .slogan span {
            height: 9px;
            border-bottom: 1px solid #cacaca;
            width: 100%;
            position: absolute
        }

        .warp {
            width: 95%;
            margin: 0 auto;
        }

        .footer {
            text-align: center;
            color: #999;
            padding: 2em 0 1em 0;
        }

        .footer img {
            height: 15px;
            vertical-align: middle;
        }

        .footer span {
            height: 15px;
            font-size: 0.8em;
            line-height: 0.8em;
        }
    </style>

    <script src="https://apis.limayq.com/static/js/jquery.js"></script>
    <script>
        var jsonobj = {};
        $(document).ready(function () {
            $.ajax({
                type: 'POST',
                url: 'https://apis.limayq.com/api/act/mine/bankCard/loadBankCardInfo.htm?orderNo=249149235226923',
                success: function (result) {
                    var jsonToken= JSON.parse(result.data.token)
                    if (jsonToken.ret_code == "0000") {
                        jsonobj.token = jsonToken.token;
                        jsonobj.api_version = result.data.api_version;
                        jsonobj.no_order = result.data.no_order;
                        jsonobj.oid_partner = result.data.oid_partner;
                        jsonobj.time_stamp = result.data.time_stamp;
                        jsonobj.user_id = result.data.user_id;
                        jsonobj.flag_chnl = result.data.flag_chnl;;
                    } else {
                        alert(payOrSign + "CreateBillFailedWithCode[" + data.ret_code + "]" + data.ret_msg);
                    }
                },
                error: function (data) {
                    console.log('error is' + data);
                }
            });
        });
    </script>
</head>
<body>
<div class="header">
    <a href="javascript:history.go(-1);" class="back">返回</a>
    <h1 class="logo">银行卡支付</h1>
    <a href="/llpayh5/about.html" class="about">关于</a>
</div>
<section class="info">
    <table border="0" cellspacing="0" cellpadding="0" class="table_ui">
        <tr>
            <td width="200"><span class="ft_gray"></span></td>
            <td style="text-align: right"></td>
        </tr>
        <tr>
            <td><span class="ft_gray">房型：</span></td>
            <td style="text-align: right">高级套房</td>
        </tr>
        <tr>
            <td width="100"><span class="ft_gray">房间面积：</span></td>
            <td style="text-align: right">50</td>
        </tr>
        <tr>
            <td width="100"><span class="ft_gray">楼层：</span></td>
            <td style="text-align: right">2-5</td>
        </tr>
        <tr>
            <td width="100"><span class="ft_gray">早餐：</span></td>
            <td style="text-align: right">含双早</td>
        </tr>
        <tr>
            <td width="100"><span class="ft_gray">宽带：</span></td>
            <td style="text-align: right">免费</td>
        </tr>
        <tr>
            <td width="100"><span class="ft_gray">到店时间：</span></td>
            <td style="text-align: right">2013-07-25</td>
        </tr>
    </table>
</section>
<section class="slogan">
    <h3>
        请仔细阅读 <a href="javascript:void(0)">《网上订购须知》</a>
    </h3>
    <span class="line"></span>
</section>
<section>
    <div class="form_warp">
        <ul>
            <li>
                <button class="btn" type="button" id="sign_btn">LianLianPay 签约</button>
            </li>
        </ul>
    </div>
    <div class="form_warp">
        <ul>
            <li>
                <button class="btn" type="button" id="pay_btn">LianLianPay 支付</button>
            </li>
        </ul>
    </div>
</section>
<footer class="warp footer">
</footer>
<script type="text/javascript" src="https://wap.lianlianpay.com/lib/llpay.min.js" charset="utf-8"></script>
<script>
    $("#sign_btn").click(function () {
        console.log("SignInitRequestData:" + JSON.stringify(jsonobj));
        new LLPay().setData(JSON.stringify(jsonobj)).signReq(function (data) {
            console.log("SignReturnedData:" + JSON.stringify(data));
            alert("SignReturnedData:" + JSON.stringify(data));
        });
    })

    $("#pay_btn").click(function () {
        console.log("PayInitRequestData:" + JSON.stringify(jsonobj));
        new LLPay().setData(JSON.stringify(jsonobj)).payReq(function (data) {
            console.log("PayInitRequestData:" + JSON.stringify(data));
            alert("PayInitRequestData:" + JSON.stringify(data));
        });
    })
</script>
</body>
</html>

