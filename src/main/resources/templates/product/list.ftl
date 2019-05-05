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
                                    <th>商品id</th>
                                    <th>名称</th>
                                    <th>图片</th>
                                    <th>单价</th>
                                    <th>库存</th>
                                    <th>描述</th>
                                    <th>类目</th>
                                    <th>创建时间</th>
                                    <th>修改时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list productInfoPageInfo.list as productInfo>
                                    <tr>
                                        <td>${productInfo.productId}</td>
                                        <td>${productInfo.productName}</td>
                                        <td><img width="80" height="50" src="${productInfo.productIcon}"></td>
                                        <td>${productInfo.productPrice}</td>
                                        <td>${productInfo.productStock}</td>
                                        <td>${productInfo.productDescription}</td>
                                        <td>${productInfo.categoryType}</td>
                                        <td>${productInfo.createTime?string('yyyy-MM-dd hh:mm:ss')}</td>
                                        <td>${productInfo.updateTime?string('yyyy-MM-dd hh:mm:ss')}</td>
                                        <td>
                                            <a href="/sell/seller/product/index?productId=${productInfo.productId}">修改</a>
                                            <#if productInfo.getProductStatusEnum().message == "上架">
                                                <a href="/sell/seller/product/off_sale?productId=${productInfo.productId}">下架</a>
                                            <#else>
                                                <a href="/sell/seller/product/on_sale?productId=${productInfo.productId}">上架</a>
                                            </#if>
                                        </td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <#--分页-->
                    <div class="col-md-12 column">
                        <ul class="pagination pull-right">
                            <#if productInfoPageInfo.hasPreviousPage == false>
                                <li class="disabled"><a href="#">上一页</a></li>
                            <#else >
                                <li><a href="/sell/seller/product/list?page=${productInfoPageInfo.prePage}&size=${productInfoPageInfo.pageSize}">上一页</a></li>
                            </#if>
                            <#list productInfoPageInfo.navigatepageNums as index>
                                <#if productInfoPageInfo.pageNum == index>
                                    <li class="disabled"><a href="#">${index}</a></li>
                                <#else >
                                    <li><a href="/sell/seller/product/list?page=${index}&size=${productInfoPageInfo.pageSize}">${index}</a></li>
                                </#if>
                            </#list>
                            <#if productInfoPageInfo.hasNextPage == false>
                                <li class="disabled"><a href="#">下一页</a> </li>
                            <#else >
                                <li><a href="/sell/seller/product/list?page=${productInfoPageInfo.nextPage}&size=${productInfoPageInfo.pageSize}">下一页</a></li>
                            </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>