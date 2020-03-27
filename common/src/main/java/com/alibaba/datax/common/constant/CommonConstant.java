package com.alibaba.datax.common.constant;

public final class CommonConstant {
    /**
     * 用于插件对自身 split 的每个 task 标识其使用的资源，以告知core 对 reader/writer split 之后的 task 进行拼接时需要根据资源标签进行更有意义的 shuffle 操作
     */
    public static String LOAD_BALANCE_RESOURCE_MARK = "loadBalanceResourceMark";

    public static final String DD_CONF = "job.setting.ddconf";

    public static final String DD_CONF_URL = "";//TODO 加入远端配置


    public static final String DATAX_JOB_CONTENT_WRITER_PARAMETER_USER = "job.content[0].writer.parameter.username";
    public static final String DATAX_JOB_CONTENT_WRITER_PARAMETER_PASS = "job.content[0].writer.parameter.password";
    public static final String DATAX_JOB_CONTENT_READER_PARAMETER_USER = "job.content[0].reader.parameter.username";
    public static final String DATAX_JOB_CONTENT_READER_PARAMETER_PASS = "job.content[0].reader.parameter.password";

}
