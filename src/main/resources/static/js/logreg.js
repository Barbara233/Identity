$(function(){
	$("#content").validate({
		rules:{
			character:{
				required:true
			},
			username:{
				required:true,
				maxlength:10
			},
			password:{
				required:true,
				digits:true,
				minlength:6
			},
			repassword:{
				required:true,
				digits:true,
				minlength:6
			}
		},
		messages:{
			character:{
				required:"用户角色不能为空！"
			},
			username:{
				required:"用户名不能为空！",
				maxlength:"用户名不得多于10位！"
			},
			password:{
				required:"密码不能为空！",
				digits:"密码必须是数字！",
				minlength:"密码不得少于6位！"
			},
			repassword:{
				required:"密码不能为空！",
				digits:"密码必须是数字！",
				minlength:"密码不得少于6位！"
			}
			
		},
		errorElement: "label", //用来创建错误提示信息标签,validate插件默认的就是label
		success: function(label) { //验证成功后的执行的回调函数
			//label指向上面那个错误提示信息标签label
			label.text(" ") //清空错误提示消息
		}
	});
});
