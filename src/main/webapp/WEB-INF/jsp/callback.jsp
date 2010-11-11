<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>


<layout:default title="StreamSavr Auth Request" >
    <jsp:attribute name="content">
             <div id="progress_bar" class="ui-progress-bar ui-container">
         <div class="ui-progress" id="lawl" style="width: 80%;">
             <span class="ui-label" style="display:none;">Processing <b class="value">79%</b></span>
         </div>
     </div>
        <%--<a id="archive_link" href="/archiver">Archive!</a>--%>
        <script type="text/javascript">

            var y = 0;


            var isDone = false;
            YAHOO.util.Connect.asyncRequest('GET', '/archiver?c', {success:function(o) {
                alert('Done');
                isDone = true;
                window.location = '<c:url value="/archiver" />';
            }});
            function update(){

                var callback = {
                    success: function(o) {
                        var jsonObject = o.responseText;
                        var prod = YAHOO.lang.JSON.parse(jsonObject);
                        //alert(prod['currentProgess']);
                        var percent = (prod.currentProgess/prod.totalTweets)*100;
                        document.getElementById('lawl').style.width = percent.toString() +'%';

                    },
                    failure: function(o) {
                        alert("failure");
                    }
                    //argument: [argument1, argument2, argument3]

                };

                if(!isDone) YAHOO.util.Connect.asyncRequest('GET', '/progress', callback, null);
            }

            setInterval(update,500);


        </script>

      </jsp:attribute>
</layout:default>

