package org.mybatis.executor;
/**
 *
 * 错误上下文
 */
public class ErrorContext {
  // 获得 \n 不同的操作系统不一样
  private static final String LINE_SEPARATOR = System.getProperty("line.separator","\n");
  //每个线程给开一个错误上下文，防止多线程问题
  private static final ThreadLocal<ErrorContext> LOCAL = new ThreadLocal<ErrorContext>();

  private ErrorContext stored;
  private String resource;
  private String activity;
  private String object;
  private String message;
  private String sql;
  private Throwable cause;
  private String daLog;

  //单例模式
  private ErrorContext() {
  }

  //工厂方法，得到一个实例
  public static ErrorContext instance() {
      //因为是多线程，所以用了ThreadLocal  线程安全
    ErrorContext context = LOCAL.get();
      //懒汉 单例模式
    if (context == null) {
      context = new ErrorContext();
      LOCAL.set(context);
    }
    return context;
  }

  //啥意思？把ErrorContext存起来供后用？并把ThreadLocal里的东西清空了？
  public ErrorContext store() {
    stored = this;
    LOCAL.set(new ErrorContext());
    return LOCAL.get();
  }

  //应该是和store相对应的方法，store是存储起来，recall是召回
  public ErrorContext recall() {
    if (stored != null) {
      LOCAL.set(stored);
      stored = null;
    }
    return LOCAL.get();
  }

  //以下都是建造者模式
  public ErrorContext resource(String resource) {
    this.resource = resource;
    return this;
  }

  public ErrorContext activity(String activity) {
    this.activity = activity;
    return this;
  }

  public ErrorContext object(String object) {
    this.object = object;
    return this;
  }

  public ErrorContext message(String message) {
    this.message = message;
    return this;
  }

  public ErrorContext sql(String sql) {
    this.sql = sql;
    return this;
  }

  public ErrorContext cause(Throwable cause) {
    this.cause = cause;
    return this;
  }
  public ErrorContext daLog(String daLog) {
    this.daLog = daLog;
    return this;
  }

  //全部清空重置
  public ErrorContext reset() {
    resource = null;
    activity = null;
    object = null;
    message = null;
    sql = null;
    cause = null;
    daLog = null;
    LOCAL.remove();
    return this;
  }

  //打印信息供人阅读
  @Override
  public String toString() {
    StringBuilder description = new StringBuilder();

    // message
    if (this.message != null) {
      description.append(LINE_SEPARATOR);
      description.append("### ");
      description.append(this.message);
    }

    // resource
    if (resource != null) {
      description.append(LINE_SEPARATOR);
      description.append("### The error may exist in ");
      description.append(resource);
    }

    // object
    if (object != null) {
      description.append(LINE_SEPARATOR);
      description.append("### The error may involve ");
      description.append(object);
    }

    // activity
    if (activity != null) {
      description.append(LINE_SEPARATOR);
      description.append("### The error occurred while ");
      description.append(activity);
    }

    // activity
    if (sql != null) {
      description.append(LINE_SEPARATOR);
      description.append("### SQL: ");
      //把sql压缩到一行里
      description.append(sql.replace('\n', ' ').replace('\r', ' ').replace('\t', ' ').trim());
    }

    // daLog
    if (daLog != null) {
      description.append(LINE_SEPARATOR);
      description.append("### DaLog: ");
      description.append(daLog);
    }


    // cause
    if (cause != null) {
      description.append(LINE_SEPARATOR);
      description.append("### Cause: ");
      description.append(cause.toString());
    }

    return description.toString();
  }
}
