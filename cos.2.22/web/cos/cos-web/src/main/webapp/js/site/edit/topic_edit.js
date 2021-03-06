layui.use(['form','element'], function(){
	form = layui.form;
	var  element=layui.element;
	form.on("radio(showmodel)", function(data){
		 if(data.elem.checked){
		   smodel=data.value;
		 }
		}); 
	form.on("switch(public)", function(data){
	     var status_=2;
		 if(data.elem.checked){
			 status_=1;
		 } 
		 $.ajax({
				url : localhostPath+"edit/topic/uppublic",
				dataType:"json",
				data :{"id":$("#topic-id").val(),"status":status_},
				async:false,
				method : "post",
				success : function(json) {
					  if(json.isPass=="ok"){
						 layer.msg(json.msg);
						}else{
							 layer.msg(json.warn);
							 if(json.status==1){
								 $("#isPublic").prop("checked",true);
							  }else{
								 $("#isPublic").prop("checked",false);
							 }
							 form.render();
						} 
				} ,error:function(XMLHttpRequest, textStatus, errorThrown){
		 	    	 if(XMLHttpRequest.status==500){
		 	    		 layer.msg(XMLHttpRequest.responseText);
		 	    	 }else{
		 	    		 layer.msg("歇工了,出了点故障...");
						}
		 	     }
			}); 
	});
	form.on("switch(istop)", function(data){
	     var top_=0;
		 if(data.elem.checked){
			 top_=1;
		 } 
		 $.ajax({
				url : localhostPath+"edit/topic/uptop",
				dataType:"json",
				data :{"id":$("#topic-id").val(),"top":top_},
				async:false,
				method : "post",
				success : function(json) { 
					if(json.isPass=="ok"){
					 layer.msg(json.msg);
					}else{
					 layer.msg(json.warn);
					 if(json.top==1){
						 $("#isTop").prop("checked",true);
					  }else{
						 $("#isTop").prop("checked",false);
					 }
					 form.render();
					}
				} ,error:function(XMLHttpRequest, textStatus, errorThrown){
		 	    	 if(XMLHttpRequest.status==500){
		 	    		 layer.msg(XMLHttpRequest.responseText);
		 	    	 }else{
		 	    		 layer.msg("歇工了,出了点故障...");
						}
		 	     }
			}); 
	});
	form.on("switch(carusel)", function(data){
	     var isCarousel=0;
		 if(data.elem.checked){
			 isCarousel=1;
		 } 
		 $.ajax({
				url : localhostPath+"edit/topic/upcarousel",
				dataType:"json",
				data :{"id":$("#topic-id").val(),"carousel":isCarousel},
				async:false,
				method : "post",
				success : function(json) { 
					if(json.isPass=="ok"){
					 layer.msg(json.msg);
					}else{
					 layer.msg(json.warn);
					 if(json.carousel==1){
						 $("#isCarousel").prop("checked",true);
					  }else{
						 $("#isCarousel").prop("checked",false);
					 }
					 form.render();
					}
				} ,error:function(XMLHttpRequest, textStatus, errorThrown){
		 	    	 if(XMLHttpRequest.status==500){
		 	    		 layer.msg(XMLHttpRequest.responseText);
		 	    	 }else{
		 	    		 layer.msg("歇工了,出了点故障...");
						}
		 	     }
			}); 
	});
	
});
var setCover=function(id,imgurl){
	$("#topic-cover-btn").attr("class","topic_edit");
	$("#topic-cover").css("background-image","url("+imghostPath+imgurl+"?t="+getTimeStamp()+")");
	$("#topic-cover-btn").data("id",id);
	$("#topic-cover-title").html("编辑封面");
}
var setEntryCover=function(id,imgurl){
	if($("#entry-"+id).length>0){
		return false;
	}
	var $entry=$("<div id=\"entry-"+id+"\" class=\"cover\"></div>");
	var btn=$("<a  class=\"topic_edit\" data-id=\""+id+"\" onclick=\"editEntry(this)\"><span style=\"font-size: 14px;font-weight: 600;display: inline-block;color:#fff\">编辑图文</span></a>");
	$entry.append(btn);
	$entry.css("background-image","url("+imghostPath+imgurl+"?t="+getTimeStamp()+")");
	$("#pic-panel").append($entry);
}
var setUpdateEntryCover=function(id,imgurl){
	$("#entry-"+id).css("background-image","url("+imghostPath+imgurl+"?t="+getTimeStamp()+")");
}
var editTopic=function(){
	if (checklogin() == false) {
		return false;
	}
	if($("#topic-name").val().length==0){
		layer.msg("主题为必须填写的哦!");
		return false;
	}
	var t_id=$("#topic-id").val();
	var url=localhostPath+"edit/topic/cover/edit/"+t_id;
	var index=layer.open(
			 {
				    type: 2,
				    title: '图文发布',
				    fix: false,
				    maxmin: true,
				    shadeClose: false,
				    shade:0.2,
				    content: url,
				    zIndex: layer.zIndex, 
			        success: function (layero) {
			            layer.setTop(layero);
			        },area : [ '1300px', '600px' ],
				    end: function(){
				    }
				  }
		    );
	return false;
  }
  var updateTopic=function(){
	  if (checklogin() == false) {
			return false;
		}
	  var title=$("#topic-name").val();
	  if(title.length==0){
		  layer.msg("主题为必须填写的哦!");
		  $("#topic-name").focus();
		  return false;
	  }
	  if(title.length>30){
		  layer.msg("标题不能大于30字哦!");
		  $("#topic-name").focus();
		  return false;
	  }
	  var type=getType();
	  if(null==type){
		  layer.msg("分类必须选择哦!");
		  return false;
	  }
	  var tag=getTag();
	  if(null==tag){
		  layer.msg("标签必须选择哦!");
		  return false;
	  }
	  if(null==smodel){
		  layer.msg("显示模式必须选择哦!");
		  return false;
	  } 
	  var desc=editor.getData();
	  if(desc.length>1000){
		  layer.msg("描述不能超出1000字!");
		  return false;
	  }
	  var t_id=$("#topic-id").val();
	  var url_=localhostPath+"edit/topic/update/"+t_id;
	 
	  $.ajax({
			url : url_,
			dataType:"json",
			data :{"title":title,"desc":desc,"type":type,"tag":tag,"smodel":smodel},
			async:false,
			method : "post",
			success : function(json) {
				if(json.isPass=="ok"){
				 $("#topic-id").val(json.topic);
				 $("#create_btn").addClass("layui-btn-disabled");
				 $("#topic-name").attr("disabled","disabled");
				 $("#topic-desc").attr("disabled","disabled");
				 $("#msg").html(json.msg);
				}else{
					$("#msg").html(json.warn);
				}
			} ,error:function(XMLHttpRequest, textStatus, errorThrown){
	 	    	 if(XMLHttpRequest.status==500){
	 	    		 layer.msg(XMLHttpRequest.responseText);
	 	    	 }else{
	 	    		 layer.msg("歇工了,出了点故障...");
					}
	 	     }
			
		}); 
	  return false;
  }
  var removeTopic=function(){
		 if (checklogin() == false) {
				return false;
			};
	  layer.confirm('确认删除这个主题？', {
		  btn: ['确定','取消'] //按钮
		}, function(index){
			  $.ajax({
					url : localhostPath+"edit/topic/remove/"+$("#topic-id").val(),
					dataType:"json",
					data :{},
					async:false,
					method : "post",
					success : function(json) {
						if(json.isPass=="ok"){
							layer.alert('删除成功!', function(index){
								  window.location.href=localhostPath+"edit/topic/create";
								  layer.close(index);
								});
						}else{
						  layer.msg(json.warn);
						}
					} ,error:function(XMLHttpRequest, textStatus, errorThrown){
			 	    	 if(XMLHttpRequest.status==500){
			 	    		 layer.msg(XMLHttpRequest.responseText);
			 	    	 }else{
			 	    		 layer.msg("歇工了,出了点故障...");
							}
			 	     }
					
				});  
		}, function(){
		   
		});
 
  }
  var addEntry=function(){
		if (checklogin() == false) {
			return false;
		}
		var t_id=$("#topic-id").val();
		if(t_id.length==0){
			layer.msg("主题为必须先创建哦!");
			return false;
		}
		var url=localhostPath+"edit/topic/entry/add/"+t_id+"/"+smodel;
		var index=layer.open(
				  {
					    type: 2,
					    title: '图文发布',
					    fix: false,
					    maxmin: true,
					    shadeClose: false,
					    shade:0.2,
					    content: url,
					    zIndex: layer.zIndex, 
				        success: function (layero) {
				            layer.setTop(layero);
				        },area : [ '1300px', '600px' ],
					    end: function(){
					    }
					  }
			    );
		return false;
	  }
  var editEntry=function(a){
		if (checklogin() == false) {
			return false;
		}
		var t_id=$("#topic-id").val();
		if(t_id.length==0){
			layer.msg("主题为必须先创建哦!");
			return false;
		}
		var e_id=$(a).data("id");
		var url=localhostPath+"edit/topic/entry/edit/"+t_id+"/"+e_id+"/"+smodel;
		var index=layer.open(
				  {
					    type: 2,
					    title: '图文编辑',
					    fix: false,
					    maxmin: true,
					    shadeClose: false,
					    shade:0.2,
					    content: url,
					    zIndex: layer.zIndex, 
				        success: function (layero) {
				            layer.setTop(layero);
				        },area : [ '1300px', '600px' ],
					    end: function(){
					    }
					  }
			    );
		return false;
	  }
  var removeEntry=function(id){
	  if (checklogin() == false) {
			return false;
		}
	  $("#entry-"+id).remove();
	  layer.msg("所选图文删除成功!");
  }
  var getType=function(){
	  var types=$("#topic_type .current");
	  if(types.length==0){
		  return null;
	  }
	  return $(types[0]).data("id");
  }
  
  var getTag=function(){
	  var tags=$("#topic_tags .current");
	  if(tags.length==0){
		  return null;
	  }
	  return $(tags[0]).data("id");
  }
  var editor;
  $(function(){
	  
     //效果渲染
	  $("#topic_type a").on("click",function(e){
		    var a=e.target;
			if(a.tagName=="A" && $(a).attr("class")=="type"){
			 $("#topic_type a").attr("class","type");	
			 $(a).attr("class","current");
			 loadTags($(a).data("id"));
			 return false;
			}
			if(a.tagName=="A" && $(a).attr("class")=="current"){
				 $(a).attr("class","type");
				 loadTags($(a).data("id"));
				 return false;
			}
		});
	  $("#topic_tags a").on("click",function(e){
		    var a=e.target;
			if(a.tagName=="A" && $(a).attr("class")=="tag"){
			 $("#topic_tags a").attr("class","tag");	
			 $(a).attr("class","current");
			 return false;
			}
			if(a.tagName=="A" && $(a).attr("class")=="current"){
				 $(a).attr("class","tag");
				 return false;
			}
		}); 
	  function loadTags(type_){
		  var url_=localhostPath+"edit/topic/loadtags";
		  $.ajax({
				url : url_,
				dataType:"html",
				data :{"type":type_},
				async:false,
				method : "post",
				success : function(html) {
				 $("#topic_tags").html(html);
				  $("#topic_tags a").on("click",function(e){
					    var a=e.target;
						if(a.tagName=="A" && $(a).attr("class")=="tag"){
						 $("#topic_tags a").attr("class","tag");	
						 $(a).attr("class","current");
						 return false;
						}
						if(a.tagName=="A" && $(a).attr("class")=="current"){
							 $(a).attr("class","tag");
							 return false;
						}
					}); 
				} ,error:function(XMLHttpRequest, textStatus, errorThrown){
		 	    	 if(XMLHttpRequest.status==500){
		 	    		 layer.msg(XMLHttpRequest.responseText);
		 	    	 }else{
		 	    		 layer.msg("歇工了,出了点故障...");
						}
		 	     }
			}); 
	  }
  })