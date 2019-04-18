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
                                    <th>订单id</th>
                                    <th>姓名</th>
                                    <th>手机号</th>
                                    <th>地址</th>
                                    <th>金额</th>
                                    <th>订单状态</th>
                                    <th>支付状态</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list orderDTOPageInfo.list as orderDTO>
                                    <tr>
                                        <td>${orderDTO.orderId}</td>
                                        <td>${orderDTO.buyerName}</td>
                                        <td>${orderDTO.buyerPhone}</td>
                                        <td>${orderDTO.buyerAddress}</td>
                                        <td>${orderDTO.orderAmount}</td>
                                        <td>${orderDTO.getOrderStatusEnum().getMessage()}</td>
                                        <td>${orderDTO.getPayStatusEnum().getMessage()}</td>
                                        <td>${orderDTO.createTime?string('yyyy-MM-dd hh:mm:ss')}</td>
                                        <td>
                                            <a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a>
                                            <#if orderDTO.getOrderStatusEnum().message == "新订单">
                                                <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
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
                            <#if orderDTOPageInfo.hasPreviousPage == false>
                                <li class="disabled"><a href="#">上一页</a></li>
                            <#else >
                                <li><a href="/sell/seller/order/list?page=${orderDTOPageInfo.prePage}&size=${orderDTOPageInfo.size}">上一页</a></li>
                            </#if>
                            <#list orderDTOPageInfo.navigatepageNums as index>
                                <#if orderDTOPageInfo.pageNum == index>
                                    <li class="disabled"><a href="#">${index}</a></li>
                                <#else >
                                    <li><a href="/sell/seller/order/list?page=${index}&size=${orderDTOPageInfo.size}">${index}</a></li>
                                </#if>
                            </#list>
                            <#if orderDTOPageInfo.hasNextPage == false>
                                <li class="disabled"><a href="#">下一页</a> </li>
                            <#else >
                                <li><a href="/sell/seller/order/list?page=${orderDTOPageInfo.nextPage}&size=${orderDTOPageInfo.size}">下一页</a></li>
                            </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>