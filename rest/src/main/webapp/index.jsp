<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <script type="text/javascript" src="${path}/js/jquery-3.4.1.min.js"></script>
    <script>
        $(function(){
            $.ajax({
                url:"${path}/test/update",
                contentType:"application/json",
                data:JSON.stringify({id:"1",age:20,salary:10,birthday:"1997-10-20"}),
                dataType:"json",
                method:"post",
                success:(result)=>{
                    console.log(result);
                }
            });
        });
    </script>
</head>
<body>
<h2>Hello World!</h2>

</body>
</html>
