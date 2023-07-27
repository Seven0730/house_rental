//日期范围选择时的快捷项
export const pickerRangOptions = {
    /*disabledDate(time) {
        //今日以后的日期不可选
        return time.getTime()  > Date.now();
    },*/
    shortcuts: [{
        text: '最近一周',
        value: () => {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            return [start, end]
        }
    }, {
        text: '最近一个月',
        value: () => {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            return [start, end]
        }
    }, {
        text: '最近三个月',
        value: () => {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            return [start, end]
        }
    }, {
        text: '最近一年',
        value: () => {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 365);
            return [start, end]
        }
    }]
}
//日期范围选择时的快捷项
export const pickerRangNoAfterOptions = {
    disabledDate(time) {
        //今日以后的日期不可选

        let day = new Date().toLocaleDateString()//取日期格式 2019/03/09
        let day_int = new Date(day + ' 23:59:59').getTime()//当天24点以前
        return time.getTime() > day_int;
    },
    shortcuts: [{
        text: '最近一周',
        value: () => {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            return [start, end]
        }
    }, {
        text: '最近一个月',
        value: () => {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            return [start, end]
        }
    }, {
        text: '最近三个月',
        value: () => {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            return [start, end]
        }
    }, {
        text: '最近一年',
        value: () => {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 365);
            return [start, end]
        }
    }]
}
//今日以后的日期不可选
export const pickerDayNoAfterOptions = {
    disabledDate(time) {
        //今日以后的日期不可选

        let day = new Date().toLocaleDateString()//取日期格式 2019/03/09
        let day_int = new Date(day + ' 23:59:59').getTime()//当天24点以前
        return time.getTime() > day_int;
    },
    shortcuts: [{
        text: '今天',
        value: () => {
            return new Date()
        }
    }, {
        text: '昨天',
        value: () => {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            //console.log(date)
            return date
        }
    }, {
        text: '一周前',
        value: () => {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            return date
        }
    }]
}
//今日以后的日期可选
export const pickerDayUseAfterOptions = {
    /*disabledDate(time) {
        //今日以后的日期不可选
        return time.getTime() > Date.now();
    },*/
    shortcuts: [{
        text: '今天',
        value: () => {
            return new Date()
        }
    }, {
        text: '昨天',
        value: () => {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            return date
        }
    }, {
        text: '一周前',
        value: () => {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            return date
        }
    }]
}
//今日以前的日期不可选
export const pickerDayNobefoOptions = {
    disabledDate(time) {
        //今日以后的日期不可选
        return time.getTime() <Date.now();
    },
    shortcuts: [{
        text: '今天',
        onClick(picker) {
            picker.$emit('pick', new Date());
        }
    }, {
        text: '昨天',
        onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
        }
    }, {
        text: '一周前',
        onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
        }
    }]
}

