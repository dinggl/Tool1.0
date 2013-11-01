define(function(require, exports, module){
module.exports = [
		{
			text : '请选择*',
			value : 'null',
			children : [ {
				text : '请选择*',
				value : 'null'
			} ]
		},
		{{#map}}
		{
			text : '{{city}}',
			value : '{{city}}',
			children : [
			{{#list}}
					{
						text : '{{name}}',
						value : '{{name}}',
						children : [ {
							title_left : '查找我们位于中国的专卖店',
							title_right : '{{name}}',
							enable_bmap : false,
							addr : '{{address}}',
							tel : '{{tel}}',
							opening : '{{opening}}',
							rcv_url : '{{url}}'
						} ]
					},
			{{/list}}
			]
	},
	{{/map}}
	];
})