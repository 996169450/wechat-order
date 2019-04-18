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
                    <form role="form" method="post" action="/sell/seller/product/save_or_update">
                        <div class="form-group">
                            <label for="inputName">商品名称</label>
                            <input name="productName" type="text" class="form-control" id="inputName" value="${(productInfo.productName)!""}"/>
                        </div>
                        <div class="form-group">
                            <label for="inputPrice">单价</label>
                            <input name="productPrice" type="text" class="form-control" id="inputPrice" value="${(productInfo.productPrice)!""}"/>
                        </div>
                        <div class="form-group">
                            <label for="inputStock">库存</label>
                            <input name="productStock" type="number" class="form-control" id="inputStock" value="${(productInfo.productStock)!""}"/>
                        </div>
                        <div class="form-group">
                            <label for="inputDescription">描述</label>
                            <input name="productDescription" type="text" class="form-control" id="inputDescription" value="${(productInfo.productDescription)!""}"/>
                        </div>
                        <div class="form-group">
                            <label for="inputIcon">图片</label>
                            <img width="100" height="100" src="${(productInfo.productIcon)!""}">
                            <input name="productIcon" type="text" class="form-control" id="inputStock" value="${(productInfo.productIcon)!""}"/>
                        </div>
                        <div class="form-group">
                            <label for="inputCategoryType">类目</label>
                            <select name="categoryType" class="form-control" id="inputCategoryType">
                                <#list productCategoryList as category>
                                    <option value="${category.categoryType}"
                                            <#if (productInfo.categoryType)?? && productInfo.categoryType == category.categoryType>
                                            selected
                                            </#if>
                                            >
                                        ${category.categoryName}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <input type="hidden" name="productId" value="${(productInfo.productId)!""}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>