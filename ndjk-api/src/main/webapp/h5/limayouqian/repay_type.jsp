<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><!doctype html>
<html lang="en">
  <head>
    <meta charset="UTF-8"/>
    <title>还款方式</title>
    <meta name="keywords" content="借款,小额借钱,借款app,急用钱,短期快速放款,极速借款借钱">
    <meta name="description" content="专注于为个人提供正规小额借款、无抵押借款、个人借款、闪电借钱等服务">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=0">
    <script src="/static/js/flexable.js"></script>
    <link href="/static/css/style1.css" rel="stylesheet"/>
	    <script src="/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/spin.js"></script>
    <script type="text/javascript" src="/static/js/common.js"></script>
    <script>
      var _hmt = _hmt || [];
      (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?985acfc678db5c774efb3ed1a2235b53";
        var s = document.getElementsByTagName("script")[0]; 
        s.parentNode.insertBefore(hm, s);
      })();
  </script>
  </head>
  <body>
    <div class="repayment-description">
    <h3>到期自动扣款</h3>
    <ul>
      <li>平台如何进行自动扣款？
        <p>若在借款期限内未主动发起还款，则平台会在还款日当天从绑定银行卡中扣除所借款项，扣款成功后会向注册手机号发送短信提醒。</p>
      </li>
      <li>平台自动扣款的时间段有哪些？
        <p>平台会在还款日当天的四个时间段：12:00、16:00、22:00、01:00，向您绑定的银行卡进行自动扣款。</p>
      </li>
      <li>银行卡账户余额不足会影响自动扣款吗?
        <p>银行卡账户余额不足，会导致平台扣款失败，可能会造成逾期，请保证在扣款之前银行卡账户资金充足。</p>
      </li>
    </ul>
  </div>
  <script type="text/javascript" src="/static/js/config.js" ></script>
  <script>
  $(function() {
    $("li").each(function(k,v){
      $(v).attr('id',k); 
    })
    $("li").click(function(){
      $(this).toggleClass("active");
      var id = $(this).attr('id');
      var lis = $('ul li').filter(function(i,e){
         return $(e).attr('id') != id;
      })
      lis.removeClass();
      
   });
 })
 $('.bank').text(getBank2());
 $('.airPay').text(getAirpay());
 $('.phone').text(getPhone());
 </script>  </body>
</html>
