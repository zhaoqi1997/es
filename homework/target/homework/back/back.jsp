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
            $("#inp").click(()=>{
                $("#warn-div").empty();
            });
            //表格初始化
            $("#userlist").jqGrid({
                styleUI: "Bootstrap",//设置为bootstrap风格的表格
                url: "${path}/poem/selectAll",//获取服务端数据url 注意获取结果要json
                datatype: "json",//预期服务器返回结果类型
                mtype: "post",//请求方式
                colNames: ["ID", "诗词名", "作者", "类型", "来源", "内容", "作者简介", "类别"],//列名称数组
                colModel: [
                    {name: "id", align: 'center', width: 110},//colModel中全部参数都写在列配置对象
                    {name: "name", editable: true, width: 80},
                    {name: "author", editable: true, width: 30},
                    {name: "type", editable: true, width: 25},
                    {name: "origin", editable: true, width: 30},
                    {name: "content", editable: true, width: 200},
                    {
                        name: "authordes", editable: true, //
                        formatter: function (value, options, row) {
                            if (!row.authordes) return "不详";
                            else return row.authordes;
                        }
                    },
                    {
                        name: "cate.id", editable: true, //
                        edittype: "select",//格式为select格式
                        // multiple:true,  //设置多选
                        editoptions: { //1.本地方式获取数据   //2.远程方式获取数据
                            dataUrl: "${path}/cate/select"// "<select><option value="21">研发部</option></select>"//获取所有部门 远程数据获取的不是json数据 获取是html标签字符串
                        },
                        formatter: function (value, options, row) {
                            if (row.cate) return row.cate.name;
                        }
                    }
                ],//列数组值配置列对象
                pager: "#pager",//设置分页工具栏html
                // 注意: 1.一旦设置分页工具栏之后在根据指定url查询时自动向后台传递page(当前页) 和 rows(每页显示记录数)两个参数
                rowNum: 10,//这个代表每页显示记录数
                rowList: [10, 15, 20],//生成可以指定显示每页展示多少条下拉列表
                viewrecords: true,//显示总记录数
                caption: "唐诗宋词列表",//表格标题
                // cellEdit: true,//开启单元格编辑功能
                editurl: "${path}/poem/opera",//开启编辑时执行编辑操作的url路径  添加  修改  删除
                autowidth: true,//自适应外部容器
                height: 390//指定表格高度
            }).navGrid("#pager",
                {width: 30},
                {add: true, edit: true, del: true, refresh: true},
                {closeAfterAdd: true},
                {closeAfterDel: true},
                {closeAfterEdit: true, reloadAfterSubmit: true}
            );//开启增删改工具按钮  注意:1.这里存在一个bug surl为实现
            $.ajax({
                url: "${path}/redis/select",
                method: "post",
                dateType: "json",
                success: (data) => {
                    $.each(data, (i, s) => {
                        if (s.score > 1) {
                            $btn = $('<button class="btn btn-primary" type="button"> ' + s.value + ' <span class="badge" style="color: red">' + s.score + '</span></button>');

                        } else {
                            $btn = $('<button class="btn btn-primary" type="button"> ' + s.value + ' <span class="badge">' + s.score + '</span></button>');
                        }
                        $("#keyword").append($btn).append("&nbsp;");
                    });
                }
            });
            $("#btn").click(() => {
                let w = $("#btn").prev().val();
                if (w=="") {
                    $("#warn-div").append($('<p class="bg-danger" style="width: 100px">不能为空哦！</p>'));
                    return;
                }
                $("#btn").prev().val("");
                $.ajax({
                    url: "${path}/es/insert",
                    dateType: "json",
                    data: "word=" + w,
                    success: () => {
                        xx();
                    }
                });
            });
            xx();

            function xx() {
                $.ajax({
                    url: "${path}/es/find",
                    datatype: "json",
                    success: (data) => {
                        $("#word1").empty();
                        for (let i = 0; i < data.length; i++) {

                            let $b = $('<div style="margin-left: 10px" class="col-sm-3 alert alert-warning alert-dismissible" role="alert">\n' +
                                '<button type="button" class="close" title="' + data[i] + '" data-dismiss="alert" aria-label="Close"><span\n' +
                                'aria-hidden="true">&times;</span></button><strong>' + data[i] + '</strong></div>');
                            if (data[i].length > 3) {
                                $b.attr("class", "col-sm-3 alert alert-info alert-dismissible");
                            }
                            $("#word1").append($b);
                        }
                    }
                });
            }

            $("#word1").on("click", "button", (e) => {
                let s = $(e.currentTarget).prop("title");
                console.log(s);
                $.ajax({
                    url: "${path}/es/delete",
                    data: "word=" + s,
                    datatype: "json",
                });
            });
            $("#clear").click(()=>{
                $.ajax({
                    url:"${path}/es/clear"
                });
            });
            $("#build").click(()=>{
                $.ajax({
                    url:"${path}/es/build"
                });
            });
        });
    </script>
</head>
<body>

<div class="control-label">
    <div class="row clearfix" >
        <div class="page-header col-sm-12 column" style="background-color: lightgray;height: 40px;" >
            <span style="color: gray">唐诗宋词后台管理系统V1.0</span>
            <button type="button" class="btn btn-warning" id="clear">清空ES全部文档</button>
            <button type="button" class="btn btn-primary" id="build">基于基础数据重建ES索引库</button>
        </div>
    </div>
    <div class="row clearfix" style="height: 120px">
        <div class="col-sm-12 column">
            <!--创建表格-->
            <table id="userlist"></table>
            <!--分页工具栏-->
            <div id="pager"></div>
        </div>
    </div>
    <div class="row clearfix">

        <div class="col-sm-6 column" style="height: 400px;padding-top: 20px">
            <div class="panel panel-default" style="height: 300px;">
                <div class="panel-heading">全网热搜榜：</div>
                <div class="panel-body" id="keyword">
                </div>
            </div>
        </div>
        <div class="col-sm-6 column" style="height: 350px;">
            <div style="padding-top: 20px">
                <input type="text" name="word" style="width: 300px;height: 35px"
                       placeholder="输入热词..." id="inp">
                <button id="btn" type="button" class="btn btn-success">添加远程词典</button>
                <div id="warn-div">
                    
                </div>
            </div>
            <div class="col-sm-12 column" style="height: 250px;padding-top: 20px" id="word1">

            </div>
        </div>
    </div>
</div>

</body>
</html>
