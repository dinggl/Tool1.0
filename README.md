通过excel2007或以上版本提供的数据生成商户数据，下载Tool1.0.jar 设置环境变量JAVA_TOOL_OPTIONS， 变量值为：-Dfile.encoding=UTF-8，否则出现乱码
tpl文件可以自己编写，对于商户模板文件可以直接下载tpl文件，tpl文件遵循mustache语法，Tool1.0.jar会将多余的逗号去掉。
填写完tpl路径与excel文件路径，设置sheet的名字以及key，点击convert变回生成目的数据。
其中tpl中的{{param}}与excel中的param一一对应。
举例（参照CFS  FFS store list.xlsx）：
	city	name                              	address	                                  tel	           opening	    url
	北京	北京庄胜崇光百货商场(英皇钟表珠宝)	北京宣武门外大街8号庄胜崇光百货新馆一层	(010)63105191	   10:00-22:00	http://www.omegawatches.cn/cn/stores/countries/stores-details?country=446&city=%E5%8C%97%E4%BA%AC&storetype=1&sellwatches=1
	北京	北京当代商城	                      北京市海淀区中关村大街40号	            (010)62696120	   10:00-22:00	http://www.omegawatches.cn/cn/stores/countries/stores-details?country=446&city=%E5%8C%97%E4%BA%AC&storetype=1&sellwatches=1
	北京	北京燕莎友谊商城金源店            	北京市海淀区远大路1号东侧	              (010)88873971	   10:00-21:00	http://www.omegawatches.cn/cn/stores/countries/stores-details?country=446&city=%E5%8C%97%E4%BA%AC&storetype=1&sellwatches=1
	北京	北京双安商场	                      北京市海淀区北三环西路38号	            (010)62138820	   10:00-22:00	http://www.omegawatches.cn/cn/stores/countries/stores-details?country=446&city=%E5%8C%97%E4%BA%AC&storetype=1&sellwatches=1
	北京	北京翠微大厦	                      北京市海淀区翠微大厦一楼	              (010)68168185	   10:00-22:00	http://www.omegawatches.cn/cn/stores/countries/stores-details?country=446&city=%E5%8C%97%E4%BA%AC&storetype=1&sellwatches=1
	北京	北京赛特购物中心                  	北京市朝阳区建国门外大街22号	          (010)65257366	   10:00-22:00	http://www.omegawatches.cn/cn/stores/countries/stores-details?country=446&city=%E5%8C%97%E4%BA%AC&storetype=1&sellwatches=1
	北京	北京市百货大楼                    	北京市王府井大街255号北京百货大楼一层	  (010)85110157	   10:00-22:00	http://www.omegawatches.cn/cn/stores/countries/stores-details?country=446&city=%E5%8C%97%E4%BA%AC&storetype=1&sellwatches=1
	天津	天津市亨得利钟表眼镜有限公司	      天津市和平区滨江道145号	                (022)27113295	   10:00-22:00	http://www.omegawatches.cn/cn/stores/countries/stores-details?country=446&city=%E5%A4%A9%E6%B4%A5&storetype=1&sellwatches=1
  天津	天津友谊商厦	                      天津市河西区友谊路21号	                (022)60860132	   10:00-21:30	http://www.omegawatches.cn/cn/stores/countries/stores-details?country=446&city=%E5%A4%A9%E6%B4%A5&storetype=1&sellwatches=1
  天津	天津一商友谊精品广场               	天津市和平区曲阜道38号	                (022)60828187	   10:00-21:30	http://www.omegawatches.cn/cn/stores/countries/stores-details?country=446&city=%E5%A4%A9%E6%B4%A5&storetype=1&sellwatches=1
石家庄	石家庄先天下广场盛时表行	          石家庄市中山东路326号	                  (0311)85936689	 10:00-21:30	http://www.omegawatches.cn/cn/stores/search-on-map/store-details/5787
  济南	济南贵和购物中心	                  济南市天地坛街1号	                      (0531)80982603	 09:30-21:30	http://www.omegawatches.cn/cn/stores/countries/stores-details?country=446&city=%E6%B5%8E%E5%8D%97&storetype=1&sellwatches=1
  济南	山东银座商城	                      济南市泺源大街66号                      (0531)66228126	 09:30-21:30	http://www.omegawatches.cn/cn/stores/countries/stores-details?country=446&city=%E6%B5%8E%E5%8D%97&storetype=1&sellwatches=1
  济南	济南恒隆广场	                      济南市历下区泉城路188号                 (0531)55638166	 10:00-22:00	http://www.omegawatches.cn/cn/stores/countries/stores-details?country=446&city=%E6%B5%8E%E5%8D%97&storetype=1&sellwatches=1
  青岛	青岛阳光百货                      	青岛市香港中路38号	                    (0532)86677535	  10:00-22:00	http://www.omegawatches.cn/cn/stores/countries/stores-details?country=446&city=%E9%9D%92%E5%B2%9B&storetype=1&sellwatches=1

tpl配置为：
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
							url : '{{url}}'
						} ]
					},
			{{/list}}
			]
	},
	{{/map}}
	];
	
	我们设置key为city，则会用数据
	{
	    map：[
	       {
	          city: '北京',
	          list: [{
	              city:'北京',
	              'name':'北京庄胜崇光百货商场(英皇钟表珠宝)',
	              'address':'北京宣武门外大街8号庄胜崇光百货新馆一层',
	              'tel':'(010)63105191',
	              'opening':'10:00-22:00',
	              'url':'http://www.omegawatches.cn/cn/stores/countries/stores-details?country=446&city=%E5%8C%97%E4%BA%AC&storetype=1&sellwatches=1'
	            },
	            {
	              city:'北京',
	              'name':'北京当代商城',
	              'address':'北京市海淀区中关村大街40号',
	              'tel':'....',
	              'opening':'...',
	              'url':'...'
	            },
	            {...}
	          ]
	       },
	       {
	          city: '天津',
	          list: [{
	              city:'天津',
	              'name':'...',
	              'address':'...',
	              'tel':'(010)63105191',
	              'opening':'10:00-22:00',
	              'url':'http://www.omegawatches.cn/cn/stores/countries/stores-details?country=446&city=%E5%8C%97%E4%BA%AC&storetype=1&sellwatches=1'
	            },
	            {
	              city:'...',
	              'name':'...',
	              'address':'...',
	              'tel':'....',
	              'opening':'...',
	              'url':'...'
	            },
	            {...}
	          ]
	       },
	       {
	          city: '济南',
	          list: [
	            {...}
	          ]
	       },
	       {...}
	    ]
	}
	填充tpl模板文件
