/**
 *工位管理
 */
var pageCurr;
$(function(){
    layui.use('table', function(){
        var table = layui.table
        var form = layui.form

        tableIns = table.render({
            elem: '#workStationList'
            ,url:'/basic/getWorkStations'
            ,method: 'post' //默认：get请求
            ,cellMinWidth: 80
            ,page: true,
            request: {
                pageName: 'page' //页码的参数名称，默认：page
                ,limitName: 'limit' //每页数据量的参数名，默认：limit
            },response:{
                statusName: 'code' //数据状态的字段名称，默认：code
                ,statusCode: 200 //成功的状态码，默认：0
                ,countName: 'totals' //数据总数的字段名称，默认：count
                ,dataName: 'list' //数据列表的字段名称，默认：data
            }
            ,cols: [[
                {type:'numbers'}
                ,{field:'id', title:'ID',width:80, unresize: true, sort: true}
                ,{field:'factoryCode', title:'工厂号'}
                ,{field:'factoryName', title:'工厂名'}
                ,{field:'workShopCode', title: '车间号'}
                ,{field:'workShopName', title: '车间名'}
                ,{field:'workStationCode', title: '工位号'}
                ,{field:'ipAddress', title:'IP地址'}
                ,{fixed:'right', title:'操作',width:140,align:'center', toolbar:'#optBar'}
            ]]
            ,  done: function(res, curr, count){
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                //console.log(res);
                //得到当前页码
                //console.log(curr);
                //得到数据总量
                //console.log(count);
                pageCurr=curr;
            }
        })
        table.on('tool(workStationTable)',function(obj){
            var data = obj.data
            if(obj.event === 'del'){
                //删除
                delWorkStation(data, data.id, data.workStationCode)
            }else if(obj.event === 'edit'){
                //编辑
                getWorkStation(data, data.id)
            }else if(obj.event === 'recover'){
                //恢复
                recoverWorkStation(data, data.id, data.workStationCode)
            }
        })
        //监听提交
        form.on('submit(workStationSubmit)', function(data){
            // TODO 校验
            formSubmit(data);
            return false;
        })

    })
    //搜索框
    layui.use(['form'], function(){
        var form = layui.form ,layer = layui.layer
        //TODO 数据校验
        //监听搜索框
        form.on('submit(searchSubmit)', function(data){
            //重新加载table
            load(data);
            return false;
        });
    });
})



function load(obj){
    //重新加载table
    tableIns.reload({
        where: obj.field
        , page: {
            curr: pageCurr //从当前页码开始
        }
    });
}

//添加工位
function addWorkStation(){
    openWorkStation(null,"开通用户");
}

function delWorkStation(obj, id, workStationCode){
    if(null != id){
        layer.confirm('您确定要删除' + workStationCode + '工位吗？',{
            btn: ['确认', '返回']   //按钮
        },function(){
            $.post('/basic/delWorkStation',{"id": id}, function(data){
                if(isLogin(data)){
                    if(data == "ok"){
                        //回调弹框
                        layer.alert("删除成功!", function(){
                            layer.closeAll()
                            //加载load方法
                            load(obj)   //自定义
                        })
                    }else{
                        layer.alert(data, function(){
                            layer.closeAll()
                            //加载load方法
                            load(obj)   //自定义
                        })
                    }
                }
            })
        }, function(){
            layer.closeAll()
        })
    }
}

function recoverWorkStation(obj, id ,workStationCode){
    if(null != id){
        layer.confirm('您确定要恢复' + workStationCode + '工位吗？',{
            btn: ['确认', '返回']   //按钮
        },function(){
            $.post('/basic/revoverWorkStation',{"id": id}, function(data){
                if(isLogin(data)){
                    if(data == "ok"){
                        //回调弹框
                        layer.alert("恢复成功!", function(){
                            layer.closeAll()
                            //加载load方法
                            load(obj)   //自定义
                        })
                    }else{
                        layer.alert(data, function(){
                            layer.closeAll()
                            //加载load方法
                            load(obj)   //自定义
                        })
                    }
                }
            })
        }, function(){
            layer.closeAll()
        })
    }
}

function getWorkStation(obj, id){
    if(obj.del){
        layer.alert("该工位已经删除，不可进行编辑；</br>  如需编辑，请先<font style='font-weight:bold;' color='blue'>恢复</font>工位状态。");
    }else{
        //回显数据
        $.get("/basic/getWorkStation", {"id": id}, function(data){
            if(isLogin(data)){
                if(data.msg == "ok" && data.workStation != null){
                    $('#id').val(data.workStation.id == null ? '': data.workStation.id)
                    $('#factoryCode').val(data.workStation.factoryCode == null ? '': data.workStation.factoryCode)
                    $('#factoryName').val(data.workStation.factoryName == null ? '': data.workStation.factoryName)
                    $('#workShopCode').val(data.workStation.workShopCode == null ? '': data.workStation.workShopCode)
                    $('#workShopName').val(data.workStation.workShopName == null ? '': data.workStation.workShopName)
                    $('#workStationCode').val(data.workStation.workStationCode == null ? '': data.workStation.workStationCode)
                    $('#ipAddress').val(data.workStation.ipAddress == null ? '': data.workStation.ipAddress)
                    openWorkStation(id, "设置工位")
                }else{
                    //弹出错误提示
                    layer.alert(data.msg,function () {
                        layer.closeAll();
                    });
                }
            }
        })
    }
}

function openWorkStation(id, title){
    if(id == null || id == ""){
        $("#id").val("");
    }
    layer.open({
        type:1,
        title: title,
        fixed:false,
        resize :false,
        shadeClose: true,
        area: ['550px'],
        content:$('#setWorkStation'),
        end:function(){
            cleanWorkStation();
        }
    });
}

function cleanWorkStation(){
    $('#factoryCode').val('')
    $('#factoryName').val('')
    $('#workShopCode').val('')
    $('#workShopName').val('')
    $('#workStationCode').val('')
    $('#ipAddress').val('')
}

function formSubmit(obj){
    $.ajax({
        type: "POST",
        data: $("#workStationForm").serialize(),
        url: "/basic/setWorkStation",
        success: function (data) {
            if(isLogin(data)){
                if (data == "ok") {
                    layer.alert("操作成功",function(){
                        layer.closeAll();
                        cleanWorkStation();
                        //$("#id").val("");
                        //加载页面
                        load(obj);
                    });
                } else {
                    layer.alert(data,function(){
                        layer.closeAll();
                        //加载load方法
                        load(obj);//自定义
                    });
                }
            }
        },
        error: function () {
            layer.alert("操作请求错误，请您稍后再试",function(){
                layer.closeAll();
                //加载load方法
                load(obj);//自定义
            });
        }
    });


}