<html>
<#include "../common/hearder.ftl">

<body>
<#--整个页面框架-->
<div id="wrapper" class="toggled">
    <#--侧边菜单栏-->
    <#include "../common/nav.ftl">

    <#--主要内容-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/sell/seller/category/save_or_update">
                        <div class="form-group">
                            <label for="inputName">名称</label>
                            <input name="categoryName" type="text" class="form-control" id="inputName" value="${(productCategory.categoryName)!""}"/>
                        </div>
                        <div class="form-group">
                            <label for="inputStock">Type</label>
                            <input name="categoryType" type="number" class="form-control" id="inputStock" value="${(productCategory.categoryType)!""}"/>
                        </div>
                        <input type="hidden" name="categoryId" value="${(productCategory.categoryId)!""}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>