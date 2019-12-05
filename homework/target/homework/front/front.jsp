<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set scope="page" value="${pageContext.request.contextPath}" var="path"/>
<html>
<head>
    <title>Title</title>
    <!--引入 css bootstrap-->
    <link rel="stylesheet" href="${path}/css/bootstrap.min.css">
    <!--引入jqgrid的css-->
    <link rel="stylesheet" href="${path}/css/ui.jqgrid-bootstrap.css">
    <!--引入jquery-->
    <script src="${path}/js/jquery-3.4.1.min.js"></script>
    <script src="${path}/js/jquery.jqGrid.min.js"></script>
    <script src="${path}/js/grid.locale-cn.js"></script>
    <script src="${path}/js/bootstrap.min.js"></script>
    <script>
        $(function () {
            $("#btn").click(()=>{
                let value = $("#inp").val();
                console.log(value);
                $.ajax({
                    url: "${path}/es/search",
                    data: "word=" + value,
                    datatype: "json",
                    success: (data) => {
                        // console.log(data);
                        $("#showList").empty();
                        $.each(data,(i,poem)=>{
                           let $div = $("<div/>");
                           let $ul = $('<ul></ul>');
                           $ul.append($('<li style="color: #2aabd2">'+poem.name+'</li>'));
                           $ul.append($('<li>'+poem.author+"."+poem.type+'</li>'));
                           $ul.append($('<li>'+poem.content+'</li>'));
                           $ul.append($('<li>'+poem.authordes+'</li>'));
                           $div.append($ul);
                           $("#showList").append($div);
                        });
                    }
                });
            });
        });
        
    </script>
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-2 column">
        </div>
        <div class=" col-sm-8 col-sm-offset-2 column " style="padding-top: 20px">
            <h1>唐诗宋词检索系统</h1>
        </div>
        <div class="col-md-2 column">
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-2 column">
        </div>
        <div class="col-md-8 column" style="padding-top: 20px">
            <form class="form-inline">
                检索唐诗宋词：
                <div class="form-group">
                    <div class="input-group">
                        <input type="text" style="width: 500px" class="form-control" placeholder="关键词..." id="inp">
                    </div>
                </div>
                <button type="button" class="btn btn-primary" id="btn">检索</button>
            </form>
        </div>
        <div class="col-md-2 column">
        </div>
    </div>

    <div class="row clearfix">
        <div class="col-sm-12 column" id="showList">
            
        </div>
    </div>
</div>


</body>
</html>
