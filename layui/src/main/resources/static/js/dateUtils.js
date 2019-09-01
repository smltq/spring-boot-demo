/**
 * 
 * @项目名称：wyait-manage
 * @文件名称：data-utils
 * @类描述：js时间转换工具类
 * @创建时间：2018年5月24日17:13:58
 */
var DateUtils = {

	/**
	 * @描述：获取当前时间毫秒数
	 * @创建时间：2015年12月23日
	 */
	getCurrentMsTime : function() {
		var myDate = new Date();
		return myDate.getTime();
	},

	/**
	 * @描述：转换日期格式yyyy-MM-dd HH:mm:ss
	 * @创建时间：2015年12月23日
	 */
	formatDate : function(dateTime) {
		return this.formatterDateTime(dateTime);
	},

	/**
	 * @描述：时间格式转毫秒
	 * @创建时间：2015年12月23日
	 */
	dateToLongMsTime : function(date) {
		return date.getTime();
	},

	/**
	 * @描述：格式化日期（不含时间）
	 * @创建时间：2015年12月23日
	 */
	formatterDate : function(date) {
		var datetime = this.getGMTTime(date);
		var result = datetime.getFullYear()
				+ "-"// "年"
				+ ((datetime.getMonth() + 1) >= 10 ? (datetime.getMonth() + 1)
						: "0" + (datetime.getMonth() + 1))
				+ "-"// "月"
				+ (datetime.getDate() < 10 ? "0" + datetime.getDate()
						: datetime.getDate());
		return result;
	},

	/**
	 * @描述：格式化日期（含时间"00:00:00"）
	 * @创建时间：2015年12月23日
	 */
	formatterDate2 : function(date) {
		var datetime = this.getGMTTime(date);
		var result = datetime.getFullYear()
				+ "-"// "年"
				+ ((datetime.getMonth() + 1) >= 10 ? (datetime.getMonth() + 1)
						: "0" + (datetime.getMonth() + 1))
				+ "-"// "月"
				+ (datetime.getDate() < 10 ? "0" + datetime.getDate()
						: datetime.getDate()) + " " + "00:00:00";
		return result;
	},

	/**
	 * @描述：格式化去日期（含时间）
	 * @创建时间：2015年12月23日
	 */
	formatterDateTime : function(date) {
		var datetime=this.getGMTTime(date);
		var result = datetime.getFullYear()
				+ "-"// "年"
				+ ((datetime.getMonth() + 1) >= 10 ? (datetime.getMonth() + 1)
						: "0" + (datetime.getMonth() + 1))
				+ "-"// "月"
				+ (datetime.getDate() < 10 ? "0" + datetime.getDate()
						: datetime.getDate())
				+ " "
				+ (datetime.getHours() < 10 ? "0" + datetime.getHours()
						: datetime.getHours())
				+ ":"
				+ (datetime.getMinutes() < 10 ? "0" + datetime.getMinutes()
						: datetime.getMinutes())
				+ ":"
				+ (datetime.getSeconds() < 10 ? "0" + datetime.getSeconds()
						: datetime.getSeconds());
		return result;
	},

	/**
	 * @描述：时间比较（结束时间大于开始时间）
	 * @创建时间：2015年12月23日
	 */
	compareDateTime : function(startTime, endTime) {
		return ((new Date(endTime.replace(/-/g, "/"))) > (new Date(startTime
				.replace(/-/g, "/"))));
	},

	/**
	 * @描述：验证开始时间合理性（开始时间不能小于当前时间X个月）
	 * @创建时间：2015年12月23日
	 */
	compareRightStartTime : function(month, startTime) {
		var now = formatterDayAndTime(new Date());
		var sms = new Date(startTime.replace(/-/g, "/"));
		var ems = new Date(now.replace(/-/g, "/"));
		var tDayms = month * 30 * 24 * 60 * 60 * 1000;
		var dvalue = ems - sms;
		if (dvalue > tDayms) {
			return false;
		}
		return true;
	},

	/**
	 * @描述：验证开始时间合理性（结束时间与开始时间的间隔不能大于（X）个月）
	 * @创建时间：2015年12月23日
	 */
	compareEndTimeGTStartTime : function(month, startTime, endTime) {
		var sms = new Date(startTime.replace(/-/g, "/"));
		var ems = new Date(endTime.replace(/-/g, "/"));
		var tDayms = month * 30 * 24 * 60 * 60 * 1000;
		var dvalue = ems - sms;
		if (dvalue > tDayms) {
			return false;
		}
		return true;
	},

	/**
	 * @描述：获取最近几天（开始时间和结束时间值,时间往前推算）
	 * @创建时间：2015年12月23日
	 */
	getRecentDaysDateTime : function(day) {
		var daymsTime = day * 24 * 60 * 60 * 1000;
		var yesterDatsmsTime = this.getCurrentMsTime() - daymsTime;
		var startTime = this.longMsTimeConvertToDateTime(yesterDatsmsTime);
		var pastDate = this.formatterDate2(new Date(startTime));
		var nowDate = this.formatterDate2(new Date());
		var obj = {
			startTime : pastDate,
			endTime : nowDate
		};
		return obj;
	},

	/**
	 * @描述：获取今天（开始时间和结束时间值）
	 * @创建时间：2015年12月23日
	 */
	getTodayDateTime : function() {
		var daymsTime = 24 * 60 * 60 * 1000;
		var tomorrowDatsmsTime = this.getCurrentMsTime() + daymsTime;
		var currentTime = this.longMsTimeConvertToDateTime(this
				.getCurrentMsTime());
		var termorrowTime = this
				.longMsTimeConvertToDateTime(tomorrowDatsmsTime);
		var nowDate = this.formatterDate2(new Date(currentTime));
		var tomorrowDate = this.formatterDate2(new Date(termorrowTime));
		var obj = {
			startTime : nowDate,
			endTime : tomorrowDate
		};
		return obj;
	},

	/**
	 * @描述：获取明天（开始时间和结束时间值）
	 * @创建时间：2015年12月23日
	 */
	getTomorrowDateTime : function() {
		var daymsTime = 24 * 60 * 60 * 1000;
		var tomorrowDatsmsTime = this.getCurrentMsTime() + daymsTime;
		var termorrowTime = this
				.longMsTimeConvertToDateTime(tomorrowDatsmsTime);
		var theDayAfterTomorrowDatsmsTime = this.getCurrentMsTime()
				+ (2 * daymsTime);
		var theDayAfterTomorrowTime = this
				.longMsTimeConvertToDateTime(theDayAfterTomorrowDatsmsTime);
		var pastDate = this.formatterDate2(new Date(termorrowTime));
		var nowDate = this.formatterDate2(new Date(theDayAfterTomorrowTime));
		var obj = {
			startTime : pastDate,
			endTime : nowDate
		};
		return obj;
	},
	/**
	 * 将CST时间转换为GMT时间，否则时间会有误差
	 * @param date
	 */
	getGMTTime:function(date){
		var index=(date.toString()).indexOf("CST");
		if(index > 0){
			//将CST时间转换为GMT时间，否则时间会有误差
			var dateStr=date.split(" ");  
			var strGMT = dateStr[0]+" "+dateStr[1]+" "+dateStr[2]+" "+dateStr[5]+" "+dateStr[3]+" GMT+0800";  
			return new Date(Date.parse(strGMT));
		}else{
			return new Date(date);
		}
	}
};
