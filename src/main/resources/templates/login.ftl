<html>
<#include "./common/hearder.ftl">

<body>
    <#--登陆表单-->
    <div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <form role="form" method="post" action="/sell/seller/login">
                <div class="form-group">
                    <label for="InputUsername">账号</label>
                    <input name="username" type="text" class="form-control" id="InputUsername" />
                </div>
                <div class="form-group">
                    <label for="InputPassword">密码</label>
                    <input name="password" type="password" class="form-control" id="InputPassword" />
                </div>
                <#--<div class="checkbox">-->
                    <#--<label><input type="checkbox" />记住密码</label>-->
                <#--</div>-->
                <button type="submit" class="btn btn-default">登陆</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>