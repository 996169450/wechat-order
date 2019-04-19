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

        <#--弹窗-->
        <div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title" id="myModalLabel">
                            提醒
                        </h4>
                    </div>
                    <div class="modal-body">
                       你有新的订单
                    </div>
                    <div class="modal-footer">
                        <button onclick="location.reload()" type="button" class="btn btn-primary">查看</button>
                        <button onclick="javascript:document.getElementById('notice').pause()" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    </div>
                </div>
            </div>
        </div>

        <#--播放音乐-->
        <audio id="notice" loop="loop">
            <source src="/sell/mp3/song.mp3" type="audio/mpeg"/>
        </audio>
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.js"></script>
    <script src="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script>
        var websocket = null;
        if ('WebSocket' in window){
            websocket = new WebSocket('ws://79e447.natappfree.cc/sell/webSocket');
        }else (
            alert('该浏览器不支持websocket！')
        );

        websocket.onopen = function (event) {
            console.log('建立链接');
        };

        websocket.onclose = function (event) {
            console.log('关闭链接');
        };

        websocket.onmessage = function (event) {
            console.log('收到消息：'+ event.data);
            //弹窗提示
            $('#myModal').modal('show');

            //播放音乐
            document.getElementById('notice').play();
        };

        websocket.onerror = function () {
            console.log('websocket通信发生错误！');
        };

        /**
         * websocket发生异常的时候会自动调用这个方法
         * @param ev
         */
        window.onbeforeunload = function (ev) {
            websocket.close();
        }

    </script>
    </body>
</html>