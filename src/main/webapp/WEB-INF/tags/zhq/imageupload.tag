<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="输入框名称"%>
<%@ attribute name="icon" type="java.lang.String" required="false" description="icon值，MD5值"%>
<%@ attribute name="icon_format" type="java.lang.String" required="false" description="图片格式，多格式用逗号隔开"%>
<%@ attribute name="icon_size" type="java.lang.Integer" required="false" description="图片大小需小于这个值，单位KB"%>
<%@ attribute name="icon_width" type="java.lang.Integer" required="false" description="图片宽度需等于这个值，单位像素"%>
<%@ attribute name="icon_height" type="java.lang.Integer" required="false" description="图片高度需等于这个值，单位像素"%>
<%@ attribute name="cssClass" type="java.lang.String" required="false" description="css样式"%>
<input id="icon-name-${id }" name="${name }" type="hidden" value="${icon }"/>
<input id="icon-upload-${id }" type="file" style="display: none"  onchange="icon_${id}_Selected();"/>
<span id="icon-${id}" class="icon-select ${cssClass } ${cssClass}">
	<span class="oper-upload icon-plus" onclick="imgIcon_${id}(this)"></span>
	<img id="icon-img-${id }" class="" alt="" src="${resourcepath}${icon }" onerror="this.src='${ctxStatic}/images/default.png'" />
</span>
<script>
var icon_overUpdata_${id} = true;
function imgIcon_${id}(th){
	if(icon_overUpdata_${id}){
		$("#icon-upload-${id}").click();
	}else{
		$.jBox.tip('正在上传','error');
	}
}
function icon_${id}_Selected(){
    // 文件选择后触发次函数
    // input标签的files属性
   	//document.querySelector("#logo_upload").files
 	// 返回的是一个文件列表数组
 	// 获得上传文件DOM对象
	var oFiles = document.querySelector("#icon-upload-${id }").files;
	//检测是否是拖拽文件到页面的操作，检测文件长度
	if(oFiles.length == 0){ 
	    return false; 
	} 
	//检测文件是不是图片 
	if(oFiles[0].type.indexOf('image') === -1){ 
		$.jBox.tip('您选择的不是图片！','error');
	    return false; 
	} 
	// 实例化一个表单数据对象
	var formData = new FormData();
	formData.append("file", oFiles[0]);
	formData.append("format","${icon_format}");//格式
	formData.append("size","${icon_size}");//大小
	formData.append("width","${icon_width}");//宽度
	formData.append("height","${icon_height}");//高度
	icon_overUpdata_${id} = false;
	$.jBox.tip('开始上传...','loading');
	$.ajax({
		url:"${ctx}/file/imageFileUpload.json",
		type:"post",
		data:formData,
		cache: false,
		contentType: false,    //不可缺
		processData: false,    //不可缺
		success:function(data){
			if(data.code==0){
				addIcon_${id}(data.data);
				$.jBox.tip('上传成功','success');
			}else{
				$.jBox.tip(data.msg,'error');
			}
			icon_overUpdata_${id} = true;
		},error:function(e){
			$.jBox.tip('上传异常','error');
			icon_overUpdata_${id} = true;
		}
	 })
}
function addIcon_${id}(data){
	console.log(data);
	$("#icon-img-${id }").prop("src","${resourcepath}"+data.img);
	$("#icon-name-${id }").val(data.img);
}
</script>
