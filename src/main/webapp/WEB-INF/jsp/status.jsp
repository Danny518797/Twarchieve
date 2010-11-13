<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:default title="Archiver" >
    <jsp:attribute name="content">
        <div id="progress_bar" class="ui-progress-bar ui-container">
                 <div class="ui-progress" id="lawl" style="width: 80%;">
                     <span class="ui-label" style="display:none;">Processing <b class="value">79%</b></span>
                 </div>
             </div>

                <script type="text/javascript">
                    var isDone = false;
                    YAHOO.util.Connect.asyncRequest('GET', '/archiver?c', {success:function(o) {            
                        isDone = true;
                        document.getElementById('lawl').style.width = "100%";
                        window.location = '<c:url value="/archiver" />';
                    }});
                    function update(){

                        var callback = {
                            success: function(o) {
                                var jsonObject = o.responseText;
                                var prod = YAHOO.lang.JSON.parse(jsonObject);
                                var percent = (prod.currentProgess/prod.totalTweets)*100;                              
                                document.getElementById('lawl').style.width = percent.toString() +'%';
                            },
                            failure: function(o) {
                                if(!isDone) alert("failure");
                            }

                        };

                        if(!isDone) YAHOO.util.Connect.asyncRequest('GET', '/progress', callback, null);
                    }
                    setInterval(update,250);
                </script>

    </jsp:attribute>
</layout:default>