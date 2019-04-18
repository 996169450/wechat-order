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
                    <#--表格-->
                    <div class="row clearfix">
                        <div class="col-md-12 column">
                            <table class="table table-bordered table-condensed table-hover">
                                <thead>
                                <tr>
                                    <th>类目id</th>
                                    <th>名称</th>
                                    <th>Type</th>
                                    <th>创建时间</th>
                                    <th>修改时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list productCategoryList as productCategory>
                                    <tr>
                                        <td>${productCategory.categoryId}</td>
                                        <td>${productCategory.categoryName}</td>
                                        <td>${productCategory.categoryType}</td>
                                        <td>${productCategory.createTime?string('yyyy-MM-dd hh:mm:ss')}</td>
                                        <td>${productCategory.updateTime?string('yyyy-MM-dd hh:mm:ss')}</td>
                                        <td>
                                            <a href="/sell/seller/category/index?categoryId=${productCategory.categoryId}">修改</a>
                                        </td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </body>
</html>