/**
 * Created by IntelliJ IDEA.
 * User: beala
 * Date: Nov 3, 2010
 * Time: 10:48:56 AM
 * To change this template use File | Settings | File Templates.
 */
var callback = {
    success:function(response){
        var size = response.responseText;
        alert(size);
    },

failure:function(r){
    r.alert("failed");
    }
};

var async = YUI.util.Connect.asyncRequest('GET', '/status', callback);
setTimeout(1000,async);