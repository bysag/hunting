<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>首页</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/themes/default/linkbutton.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/themes/default/datebox.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/themes/icon.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
<div>
<input type="button" value="测试初始化玩家位置" id="init"/>
</div>
<script type="text/javascript">
$(function(){
		$('#init').bind('click',function(e){
			var playerArray = [{"id":"17","lon":"116.39261798227328","playerId":"CM101","lat":"39.923762951552206","city":"北京"},{"id":"17","lon":"116.40285086339358","playerId":"CM102","lat":"39.91817755312354","city":"北京"},{"id":"17","lon":"116.38934638704698","playerId":"CM103","lat":"39.91106298237813","city":"北京"},{"id":"17","lon":"116.40682748439289","playerId":"CM104","lat":"39.92485725923167","city":"北京"},{"id":"17","lon":"116.40873463982285","playerId":"CM105","lat":"39.92096448831003","city":"北京"},{"id":"17","lon":"116.39922106052565","playerId":"CM106","lat":"39.92288373327606","city":"北京"},{"id":"17","lon":"116.41063744900993","playerId":"CM107","lat":"39.913753504089875","city":"北京"},{"id":"17","lon":"116.40867897456506","playerId":"CM108","lat":"39.91998752728871","city":"北京"},{"id":"17","lon":"116.38623347517145","playerId":"CM109","lat":"39.92019050175513","city":"北京"},{"id":"17","lon":"116.38388051011054","playerId":"CM110","lat":"39.91356041586554","city":"北京"},{"id":"17","lon":"116.3847150067009","playerId":"CM111","lat":"39.919494360080904","city":"北京"},{"id":"17","lon":"116.39227133090611","playerId":"CM112","lat":"39.913476675765615","city":"北京"},{"id":"17","lon":"116.39749286349713","playerId":"CM113","lat":"39.911057078606056","city":"北京"},{"id":"17","lon":"116.40824122010456","playerId":"CM114","lat":"39.920583584008","city":"北京"},{"id":"17","lon":"116.39947568778521","playerId":"CM115","lat":"39.91494445636133","city":"北京"},{"id":"17","lon":"116.38544002765722","playerId":"CM116","lat":"39.91659976165093","city":"北京"},{"id":"17","lon":"116.39601754388609","playerId":"CM117","lat":"39.9122854056808","city":"北京"},{"id":"17","lon":"116.39762205044576","playerId":"CM118","lat":"39.912992800383954","city":"北京"},{"id":"17","lon":"116.39433502825996","playerId":"CM119","lat":"39.922136567806184","city":"北京"},{"id":"17","lon":"116.38877635566993","playerId":"CM120","lat":"39.91047145147485","city":"北京"},{"id":"17","lon":"116.3927550548836","playerId":"CM121","lat":"39.914132692803136","city":"北京"},{"id":"17","lon":"116.41002849074472","playerId":"CM122","lat":"39.91376044319957","city":"北京"},{"id":"17","lon":"116.38496085068779","playerId":"CM123","lat":"39.91650229216955","city":"北京"},{"id":"17","lon":"116.3950635951907","playerId":"CM124","lat":"39.91612751508363","city":"北京"},{"id":"17","lon":"116.39099544522026","playerId":"CM125","lat":"39.91429274420363","city":"北京"},{"id":"17","lon":"116.40743803058649","playerId":"CM126","lat":"39.92145727404673","city":"北京"},{"id":"17","lon":"116.40320347502545","playerId":"CM127","lat":"39.922204825644044","city":"北京"},{"id":"17","lon":"116.40545784508316","playerId":"CM128","lat":"39.910939014472305","city":"北京"},{"id":"17","lon":"116.38461859919735","playerId":"CM129","lat":"39.92239582312675","city":"北京"},{"id":"17","lon":"116.39433015136477","playerId":"CM130","lat":"39.925836553460755","city":"北京"},{"id":"17","lon":"116.40335875782793","playerId":"CM131","lat":"39.91799057518932","city":"北京"},{"id":"17","lon":"116.41115503201809","playerId":"CM132","lat":"39.92409035606912","city":"北京"},{"id":"17","lon":"116.39836842037272","playerId":"CM133","lat":"39.922914282522015","city":"北京"},{"id":"17","lon":"116.39364689524324","playerId":"CM134","lat":"39.92141194966484","city":"北京"},{"id":"17","lon":"116.40742324285128","playerId":"CM135","lat":"39.9106005118281","city":"北京"},{"id":"17","lon":"116.40783492808802","playerId":"CM136","lat":"39.925075819653365","city":"北京"},{"id":"17","lon":"116.39640944784988","playerId":"CM137","lat":"39.9203748147928","city":"北京"},{"id":"17","lon":"116.40432019309355","playerId":"CM138","lat":"39.91677781524989","city":"北京"},{"id":"17","lon":"116.38884847258902","playerId":"CM139","lat":"39.92232904988393","city":"北京"},{"id":"17","lon":"116.390843579671","playerId":"CM140","lat":"39.91729085910295","city":"北京"},{"id":"17","lon":"116.38448763306594","playerId":"CM141","lat":"39.92375244642224","city":"北京"},{"id":"17","lon":"116.39733055624495","playerId":"CM142","lat":"39.92182970489164","city":"北京"},{"id":"17","lon":"116.38402816181335","playerId":"CM143","lat":"39.92093812387117","city":"北京"},{"id":"17","lon":"116.39562865480285","playerId":"CM144","lat":"39.920138093992115","city":"北京"},{"id":"17","lon":"116.40599965007608","playerId":"CM145","lat":"39.921155762848215","city":"北京"},{"id":"17","lon":"116.3843772552286","playerId":"CM146","lat":"39.91113808352277","city":"北京"},{"id":"17","lon":"116.39610258435867","playerId":"CM147","lat":"39.92273364303817","city":"北京"},{"id":"17","lon":"116.39926980644111","playerId":"CM148","lat":"39.91707841521101","city":"北京"},{"id":"17","lon":"116.40030129663408","playerId":"CM149","lat":"39.91494116546141","city":"北京"},{"id":"17","lon":"116.41203289084162","playerId":"CM150","lat":"39.92580219505092","city":"北京"},{"id":"17","lon":"116.40932603663093","playerId":"CM151","lat":"39.91675749066851","city":"北京"},{"id":"17","lon":"116.3943985891997","playerId":"CM152","lat":"39.92524692959485","city":"北京"},{"id":"17","lon":"116.40377825215126","playerId":"CM153","lat":"39.923419407994636","city":"北京"},{"id":"17","lon":"116.38415175278615","playerId":"CM154","lat":"39.91761609342488","city":"北京"},{"id":"17","lon":"116.39077891330358","playerId":"CM155","lat":"39.922880780375664","city":"北京"},{"id":"17","lon":"116.38919806883082","playerId":"CM156","lat":"39.926012650109016","city":"北京"},{"id":"17","lon":"116.39622328720883","playerId":"CM157","lat":"39.9178579105433","city":"北京"},{"id":"17","lon":"116.39646950593286","playerId":"CM158","lat":"39.92413008580098","city":"北京"},{"id":"17","lon":"116.38932923206573","playerId":"CM159","lat":"39.922293017436715","city":"北京"},{"id":"17","lon":"116.39060140372901","playerId":"CM160","lat":"39.91213917108776","city":"北京"},{"id":"17","lon":"116.40133496847761","playerId":"CM161","lat":"39.92274370668125","city":"北京"},{"id":"17","lon":"116.38813037490159","playerId":"CM162","lat":"39.91985902947574","city":"北京"},{"id":"17","lon":"116.38352951684827","playerId":"CM163","lat":"39.92493348135826","city":"北京"},{"id":"17","lon":"116.4105771662015","playerId":"CM164","lat":"39.924633581710935","city":"北京"},{"id":"17","lon":"116.38898683129729","playerId":"CM165","lat":"39.91303753916849","city":"北京"},{"id":"17","lon":"116.39934010785994","playerId":"CM166","lat":"39.91350415700171","city":"北京"},{"id":"17","lon":"116.39280337244847","playerId":"CM167","lat":"39.92475555631747","city":"北京"},{"id":"17","lon":"116.38828325782937","playerId":"CM168","lat":"39.92626236432499","city":"北京"},{"id":"17","lon":"116.40055818754047","playerId":"CM169","lat":"39.919409034687156","city":"北京"},{"id":"17","lon":"116.39758444206005","playerId":"CM170","lat":"39.921750427357985","city":"北京"},{"id":"17","lon":"116.38585293090175","playerId":"CM171","lat":"39.91094630008944","city":"北京"},{"id":"17","lon":"116.39768843133645","playerId":"CM172","lat":"39.919938241173206","city":"北京"},{"id":"17","lon":"116.38444077692971","playerId":"CM173","lat":"39.912859436198936","city":"北京"},{"id":"17","lon":"116.41050566695128","playerId":"CM174","lat":"39.91950895685763","city":"北京"},{"id":"17","lon":"116.38631504589148","playerId":"CM175","lat":"39.91965907840223","city":"北京"},{"id":"17","lon":"116.39006782842198","playerId":"CM176","lat":"39.91190276174476","city":"北京"},{"id":"17","lon":"116.394947923602","playerId":"CM177","lat":"39.922789978286055","city":"北京"},{"id":"17","lon":"116.3951697192976","playerId":"CM178","lat":"39.9120921349013","city":"北京"},{"id":"17","lon":"116.38901718107863","playerId":"CM179","lat":"39.92625952826708","city":"北京"},{"id":"17","lon":"116.40271981663139","playerId":"CM180","lat":"39.92439513293953","city":"北京"},{"id":"17","lon":"116.38728573979745","playerId":"CM181","lat":"39.91917546846517","city":"北京"},{"id":"17","lon":"116.40673080503815","playerId":"CM182","lat":"39.919157762778475","city":"北京"},{"id":"17","lon":"116.38517270913063","playerId":"CM183","lat":"39.92250604621858","city":"北京"},{"id":"17","lon":"116.38888492006541","playerId":"CM184","lat":"39.92611983913655","city":"北京"},{"id":"17","lon":"116.40062863297922","playerId":"CM185","lat":"39.91849853078715","city":"北京"},{"id":"17","lon":"116.39743300489535","playerId":"CM186","lat":"39.91828317417625","city":"北京"},{"id":"17","lon":"116.39092122453687","playerId":"CM187","lat":"39.92395394290382","city":"北京"},{"id":"17","lon":"116.39348729748822","playerId":"CM188","lat":"39.92327352741382","city":"北京"},{"id":"17","lon":"116.4075475300925","playerId":"CM189","lat":"39.91446567821818","city":"北京"},{"id":"17","lon":"116.39539814552799","playerId":"CM190","lat":"39.92525823374237","city":"北京"},{"id":"17","lon":"116.40913670532325","playerId":"CM191","lat":"39.92464634330902","city":"北京"},{"id":"17","lon":"116.40320778755274","playerId":"CM192","lat":"39.926038795857494","city":"北京"},{"id":"17","lon":"116.4049681761329","playerId":"CM193","lat":"39.91887914588755","city":"北京"},{"id":"17","lon":"116.40638656521675","playerId":"CM194","lat":"39.91601522655305","city":"北京"},{"id":"17","lon":"116.3854376521708","playerId":"CM195","lat":"39.92323424569669","city":"北京"},{"id":"17","lon":"116.39397680326118","playerId":"CM196","lat":"39.911719221186544","city":"北京"},{"id":"17","lon":"116.38320698701197","playerId":"CM197","lat":"39.925031922620576","city":"北京"},{"id":"17","lon":"116.38690141492312","playerId":"CM198","lat":"39.917806683119224","city":"北京"},{"id":"17","lon":"116.40187069888661","playerId":"CM199","lat":"39.91749007909118","city":"北京"},{"id":"17","lon":"116.40492395876781","playerId":"CM200","lat":"39.914604848833974","city":"北京"},{"id":"17","lon":"116.39194955089056","playerId":"CM201","lat":"39.921592574880236","city":"北京"},{"id":"17","lon":"116.4006957798793","playerId":"CM202","lat":"39.91558991045402","city":"北京"},{"id":"17","lon":"116.39787266642426","playerId":"CM203","lat":"39.91427060446509","city":"北京"},{"id":"17","lon":"116.41210243984463","playerId":"CM204","lat":"39.917633141030414","city":"北京"},{"id":"17","lon":"116.3916036837187","playerId":"CM205","lat":"39.91674791653167","city":"北京"},{"id":"17","lon":"116.38578479699642","playerId":"CM206","lat":"39.92016261973389","city":"北京"},{"id":"17","lon":"116.40668635911615","playerId":"CM207","lat":"39.917542193112276","city":"北京"},{"id":"17","lon":"116.39490239858353","playerId":"CM208","lat":"39.915360346607486","city":"北京"},{"id":"17","lon":"116.39564824056876","playerId":"CM209","lat":"39.914270404845446","city":"北京"},{"id":"17","lon":"116.39044019075654","playerId":"CM210","lat":"39.924921191730306","city":"北京"},{"id":"17","lon":"116.41078798731365","playerId":"CM211","lat":"39.92201758718731","city":"北京"},{"id":"17","lon":"116.40006209488024","playerId":"CM212","lat":"39.91195197280125","city":"北京"},{"id":"17","lon":"116.39646239863093","playerId":"CM213","lat":"39.913821255154886","city":"北京"},{"id":"17","lon":"116.40602850957046","playerId":"CM214","lat":"39.917408370046516","city":"北京"},{"id":"17","lon":"116.40165828240731","playerId":"CM215","lat":"39.9173624535993","city":"北京"},{"id":"17","lon":"116.40452627311043","playerId":"CM216","lat":"39.91440362884643","city":"北京"},{"id":"17","lon":"116.41038264556371","playerId":"CM217","lat":"39.919222130602954","city":"北京"},{"id":"17","lon":"116.38467829511777","playerId":"CM218","lat":"39.91887931996099","city":"北京"},{"id":"17","lon":"116.3925935117862","playerId":"CM219","lat":"39.91541958430717","city":"北京"},{"id":"17","lon":"116.41031189112748","playerId":"CM220","lat":"39.92485864114411","city":"北京"},{"id":"17","lon":"116.39007178948629","playerId":"CM221","lat":"39.92572174349282","city":"北京"},{"id":"17","lon":"116.38727822572756","playerId":"CM222","lat":"39.91536565608714","city":"北京"},{"id":"17","lon":"116.38960888041687","playerId":"CM223","lat":"39.91821958138398","city":"北京"},{"id":"17","lon":"116.39502377266166","playerId":"CM224","lat":"39.91812000360239","city":"北京"},{"id":"17","lon":"116.40244142636905","playerId":"CM225","lat":"39.92638436344464","city":"北京"},{"id":"17","lon":"116.38771535434674","playerId":"CM226","lat":"39.918685005545576","city":"北京"},{"id":"17","lon":"116.39657188078661","playerId":"CM227","lat":"39.910685385984365","city":"北京"},{"id":"17","lon":"116.391006816993","playerId":"CM228","lat":"39.92484172439124","city":"北京"},{"id":"17","lon":"116.39165786106042","playerId":"CM229","lat":"39.91849251764368","city":"北京"},{"id":"17","lon":"116.3859386367532","playerId":"CM230","lat":"39.91304414861813","city":"北京"},{"id":"17","lon":"116.41225482720799","playerId":"CM231","lat":"39.91153963449034","city":"北京"},{"id":"17","lon":"116.39119363765292","playerId":"CM232","lat":"39.91178181704846","city":"北京"},{"id":"17","lon":"116.40868790684402","playerId":"CM233","lat":"39.92176389809594","city":"北京"},{"id":"17","lon":"116.40834261185248","playerId":"CM234","lat":"39.91437587790242","city":"北京"},{"id":"17","lon":"116.39706515130163","playerId":"CM235","lat":"39.91049946723462","city":"北京"},{"id":"17","lon":"116.40131220161722","playerId":"CM236","lat":"39.92582336580083","city":"北京"},{"id":"17","lon":"116.39881853415469","playerId":"CM237","lat":"39.92515117713582","city":"北京"},{"id":"17","lon":"116.40559808206943","playerId":"CM238","lat":"39.91450122257402","city":"北京"},{"id":"17","lon":"116.41150143939375","playerId":"CM239","lat":"39.924324010128835","city":"北京"},{"id":"17","lon":"116.38561804691969","playerId":"CM240","lat":"39.910702040055","city":"北京"},{"id":"17","lon":"116.38911909220181","playerId":"CM241","lat":"39.91219652288699","city":"北京"},{"id":"17","lon":"116.39377974541344","playerId":"CM242","lat":"39.91698479324835","city":"北京"},{"id":"17","lon":"116.38449458520678","playerId":"CM243","lat":"39.92073547403042","city":"北京"},{"id":"17","lon":"116.40475984642964","playerId":"CM244","lat":"39.925270708867494","city":"北京"},{"id":"17","lon":"116.40532771692355","playerId":"CM245","lat":"39.91849056517713","city":"北京"},{"id":"17","lon":"116.39647814290304","playerId":"CM246","lat":"39.91952131368049","city":"北京"},{"id":"17","lon":"116.40516154262761","playerId":"CM247","lat":"39.91690831431609","city":"北京"},{"id":"17","lon":"116.40850701629975","playerId":"CM248","lat":"39.91485467131341","city":"北京"},{"id":"17","lon":"116.38940315722975","playerId":"CM249","lat":"39.91512910715899","city":"北京"},{"id":"17","lon":"116.39209877311029","playerId":"CM250","lat":"39.915211972059424","city":"北京"},{"id":"17","lon":"116.38577569044655","playerId":"CM251","lat":"39.91292564621651","city":"北京"},{"id":"17","lon":"116.4026367795847","playerId":"CM252","lat":"39.91844647608937","city":"北京"},{"id":"17","lon":"116.40122206232289","playerId":"CM253","lat":"39.92162891157231","city":"北京"},{"id":"17","lon":"116.39552200049205","playerId":"CM254","lat":"39.91900790380756","city":"北京"},{"id":"17","lon":"116.39259142793333","playerId":"CM255","lat":"39.921791523266585","city":"北京"},{"id":"17","lon":"116.3971939971404","playerId":"CM256","lat":"39.91483503174057","city":"北京"},{"id":"17","lon":"116.39517460516686","playerId":"CM257","lat":"39.92487210316828","city":"北京"},{"id":"17","lon":"116.40524498038938","playerId":"CM258","lat":"39.91614866207651","city":"北京"},{"id":"17","lon":"116.3944509866793","playerId":"CM259","lat":"39.91058921803756","city":"北京"},{"id":"17","lon":"116.39913829816979","playerId":"CM260","lat":"39.917760165844264","city":"北京"},{"id":"17","lon":"116.40459795316002","playerId":"CM261","lat":"39.91627264393635","city":"北京"},{"id":"17","lon":"116.39992971477885","playerId":"CM262","lat":"39.92338293340552","city":"北京"},{"id":"17","lon":"116.39897328771112","playerId":"CM263","lat":"39.9123834004518","city":"北京"},{"id":"17","lon":"116.39113065282686","playerId":"CM264","lat":"39.917067635997526","city":"北京"},{"id":"17","lon":"116.40585871965423","playerId":"CM265","lat":"39.92204133817012","city":"北京"},{"id":"17","lon":"116.39734813652657","playerId":"CM266","lat":"39.923376078034934","city":"北京"},{"id":"17","lon":"116.39262390750446","playerId":"CM267","lat":"39.917316146877376","city":"北京"},{"id":"17","lon":"116.39318715624265","playerId":"CM268","lat":"39.920253622682075","city":"北京"},{"id":"17","lon":"116.39580579644048","playerId":"CM269","lat":"39.92039912491347","city":"北京"},{"id":"17","lon":"116.38654150332871","playerId":"CM270","lat":"39.92117184691122","city":"北京"},{"id":"17","lon":"116.40452460021028","playerId":"CM271","lat":"39.91731920507675","city":"北京"},{"id":"17","lon":"116.40745544571394","playerId":"CM272","lat":"39.92432459266439","city":"北京"},{"id":"17","lon":"116.38926111085047","playerId":"CM273","lat":"39.9168822188777","city":"北京"},{"id":"17","lon":"116.39923431414971","playerId":"CM274","lat":"39.915882410643626","city":"北京"},{"id":"17","lon":"116.38701862594219","playerId":"CM275","lat":"39.92153340968307","city":"北京"},{"id":"17","lon":"116.40505561007403","playerId":"CM276","lat":"39.923258102364","city":"北京"},{"id":"17","lon":"116.39810735775929","playerId":"CM277","lat":"39.9173704587058","city":"北京"},{"id":"17","lon":"116.39766612805717","playerId":"CM278","lat":"39.92078395132163","city":"北京"},{"id":"17","lon":"116.40238918780729","playerId":"CM279","lat":"39.921056447041444","city":"北京"},{"id":"17","lon":"116.41032057145989","playerId":"CM280","lat":"39.91716282001352","city":"北京"},{"id":"17","lon":"116.40585058929453","playerId":"CM281","lat":"39.922370474277265","city":"北京"},{"id":"17","lon":"116.39204709095458","playerId":"CM282","lat":"39.92275422610585","city":"北京"},{"id":"17","lon":"116.3858492392566","playerId":"CM283","lat":"39.914933154175166","city":"北京"},{"id":"17","lon":"116.40573809260475","playerId":"CM284","lat":"39.914393052355564","city":"北京"},{"id":"17","lon":"116.41041208161333","playerId":"CM285","lat":"39.91275056019063","city":"北京"},{"id":"17","lon":"116.41184688426793","playerId":"CM286","lat":"39.91793095192739","city":"北京"},{"id":"17","lon":"116.40634980436793","playerId":"CM287","lat":"39.91355745543416","city":"北京"},{"id":"17","lon":"116.40920719194355","playerId":"CM288","lat":"39.920513010083006","city":"北京"},{"id":"17","lon":"116.40853536524348","playerId":"CM289","lat":"39.918356014057956","city":"北京"},{"id":"17","lon":"116.39954037146899","playerId":"CM290","lat":"39.92349561115902","city":"北京"},{"id":"17","lon":"116.3994436375769","playerId":"CM291","lat":"39.91696735231005","city":"北京"},{"id":"17","lon":"116.40931303460695","playerId":"CM292","lat":"39.91567510552287","city":"北京"},{"id":"17","lon":"116.40751484907689","playerId":"CM293","lat":"39.91804022013433","city":"北京"},{"id":"17","lon":"116.39477141292791","playerId":"CM294","lat":"39.919567017703145","city":"北京"},{"id":"17","lon":"116.4087345018966","playerId":"CM295","lat":"39.92397188238404","city":"北京"},{"id":"17","lon":"116.38812098399389","playerId":"CM296","lat":"39.917875158752885","city":"北京"},{"id":"17","lon":"116.40828400633954","playerId":"CM297","lat":"39.926229012633556","city":"北京"},{"id":"17","lon":"116.40380190597061","playerId":"CM298","lat":"39.91119148991175","city":"北京"},{"id":"17","lon":"116.38679027987382","playerId":"CM299","lat":"39.920814441075834","city":"北京"},{"id":"17","lon":"116.41178172615398","playerId":"CM300","lat":"39.915489647768695","city":"北京"},{"id":"17","lon":"116.40210053233437","playerId":"CM301","lat":"39.9188838562575","city":"北京"},{"id":"17","lon":"116.39690198427765","playerId":"CM302","lat":"39.912552498891635","city":"北京"},{"id":"17","lon":"116.40839630333075","playerId":"CM303","lat":"39.91602453906319","city":"北京"},{"id":"17","lon":"116.39780626974573","playerId":"CM304","lat":"39.92314940606241","city":"北京"},{"id":"17","lon":"116.41122822905946","playerId":"CM305","lat":"39.91383972378006","city":"北京"},{"id":"17","lon":"116.38354184522582","playerId":"CM306","lat":"39.91476291540192","city":"北京"},{"id":"17","lon":"116.39061546791353","playerId":"CM307","lat":"39.91128769376547","city":"北京"},{"id":"17","lon":"116.395433456232","playerId":"CM308","lat":"39.92393677941402","city":"北京"},{"id":"17","lon":"116.4098496702967","playerId":"CM309","lat":"39.91789585905233","city":"北京"},{"id":"17","lon":"116.40205252019365","playerId":"CM310","lat":"39.91688847028013","city":"北京"},{"id":"17","lon":"116.39552355302007","playerId":"CM311","lat":"39.9158328080061","city":"北京"},{"id":"17","lon":"116.39539159514163","playerId":"CM312","lat":"39.91249480605354","city":"北京"},{"id":"17","lon":"116.394075180278","playerId":"CM313","lat":"39.91483259020572","city":"北京"},{"id":"17","lon":"116.38812643100418","playerId":"CM314","lat":"39.92584265511196","city":"北京"},{"id":"17","lon":"116.40248728420264","playerId":"CM315","lat":"39.91698100594004","city":"北京"},{"id":"17","lon":"116.39224307221261","playerId":"CM316","lat":"39.92635581635399","city":"北京"},{"id":"17","lon":"116.39489641709626","playerId":"CM317","lat":"39.916267071239204","city":"北京"},{"id":"17","lon":"116.40927343109689","playerId":"CM318","lat":"39.912510990669354","city":"北京"},{"id":"17","lon":"116.39434104609188","playerId":"CM319","lat":"39.92355306550609","city":"北京"},{"id":"17","lon":"116.3908521253176","playerId":"CM320","lat":"39.91284684234109","city":"北京"},{"id":"17","lon":"116.38953505470813","playerId":"CM321","lat":"39.922095064019395","city":"北京"},{"id":"17","lon":"116.39097554929522","playerId":"CM322","lat":"39.92475143594668","city":"北京"},{"id":"17","lon":"116.40936499395347","playerId":"CM323","lat":"39.91232216908975","city":"北京"},{"id":"17","lon":"116.40649094213093","playerId":"CM324","lat":"39.92184565175755","city":"北京"},{"id":"17","lon":"116.41215503986923","playerId":"CM325","lat":"39.92133330499554","city":"北京"}]
        var size = playerArray.length;
		var count = 0;
		for(var i=0;i<size;i++){
			var json = playerArray[i];
			$.get("/client/location/login", json,
			  function(data){
				console.log(count++);
				console.log(data);
			  });
		}
	     
		});
	
	});
</script>
</body>
</html>