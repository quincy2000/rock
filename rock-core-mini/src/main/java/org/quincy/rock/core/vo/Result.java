package org.quincy.rock.core.vo;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.lang3.ArrayUtils;
import org.quincy.rock.core.exception.CauseException;
import org.quincy.rock.core.exception.ParseException;
import org.quincy.rock.core.exception.ResultException;
import org.quincy.rock.core.exception.UnsupportException;
import org.quincy.rock.core.util.JsonUtil;
import org.quincy.rock.core.util.StringUtil;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * <b>结果封装类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月19日 下午3:12:21</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "rawtypes" })
public class Result<R> {
	/**
	 * TRUE。
	 */
	public static final Result<Boolean> TRUE = new BooleanResult(Boolean.TRUE);

	/**
	 * FALSE。
	 */
	public static final Result<Boolean> FALSE = new BooleanResult(Boolean.FALSE);

	/**
	 * 结果值。
	 */
	protected R result;
	/**
	 * 错误代码。
	 */
	private String errorCode;
	/**
	 * 错误文本。
	 */
	private String errorText;
	/**
	 * 错误原因。
	 */
	private String errorCause;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public Result() {
		super();
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param result 结果值
	 */
	public Result(R result) {
		this.result = result;
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param errorCode 错误代码
	 * @param errorText 错误文本
	 */
	public Result(String errorCode, String errorText) {
		this.errorCode = errorCode;
		this.errorText = errorText;
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param errorCode 错误代码
	 * @param errorText 错误文本
	 * @param errorCause 错误原因
	 */
	public Result(String errorCode, String errorText, String errorCause) {
		this.errorCode = errorCode;
		this.errorText = errorText;
		this.errorCause = errorCause;
	}

	/**
	 * <b>获得结果值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 结果值
	 */
	public R getResult() {
		return result;
	}

	/**
	 * <b>设置结果值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param result 结果值
	 */
	public void setResult(R result) {
		this.result = result;
	}

	/**
	 * <b>获得错误代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 错误代码
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * <b>设置错误代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param errorCode 错误代码
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * <b>获得错误文本。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 错误文本
	 */
	public String getErrorText() {
		return errorText;
	}

	/**
	 * <b>设置错误文本。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param errorText 错误文本
	 */
	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	/**
	 * <b>获得错误原因。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 错误原因
	 */
	public String getErrorCause() {
		return errorCause;
	}

	/**
	 * <b>设置错误原因。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param errorCause 错误原因
	 */
	public void setErrorCause(String errorCause) {
		this.errorCause = errorCause;
	}

	/**
	 * <b>是否有错误。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否有错误
	 */
	public final boolean isHasError() {
		return errorCode != null || errorText != null;
	}

	/**
	 * <b>返回结果异常。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 结果异常
	 */
	public final ResultException resultExcetion() {
		StringBuilder sb = new StringBuilder("Result error:{");
		sb.append("errorCode:'");
		sb.append(getErrorCode());
		sb.append("',errorText:'");
		sb.append(getErrorText());
		sb.append("'}");
		return new ResultException(sb.toString(), new CauseException(getErrorCause()));
	}

	/**
	 * <b>是否是分页数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否是分页数据
	 */
	public final boolean isPage() {
		return result instanceof PageSet;
	}
	
	/**
	 * <b>是否不为null。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否不为null
	 */
	public final boolean isNotNull() {
		return result != null;
	}

	/**
	 * <b>是否不是空白字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否不是空白字符串
	 */
	public final boolean isNotBlank() {
		return !isBlank();
	}

	/**
	 * <b>是否为非空。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否为非空
	 */
	public boolean isNotEmpty() {
		return !isEmpty();
	}

	/**
	 * <b>返回集合大小。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 集合大小
	 */
	public long getSize() {
		if (result == null)
			return 0;
		else if (result instanceof String)
			return ((String) result).length();
		else if (result instanceof Collection)
			return ((Collection) result).size();
		else if (result instanceof Map)
			return ((Map) result).size();
		else if (result instanceof PageSet)
			return ((PageSet) result).getCount();
		else if (result.getClass().isArray())
			return Array.getLength(result);
		else
			return 1;
	}

	/**
	 * <b>toJsonString。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return
	 */
	public String toJsonString() {
		return JsonUtil.toJson(this);
	}

	/** 
	 * toString。
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return toJsonString();
	}

	private boolean isBlank() {
		return result == null || (result instanceof String && StringUtil.isBlank((String) result));
	}

	private boolean isEmpty() {
		if (result == null)
			return true;
		else if (result instanceof String)
			return ((String) result).length() == 0;
		else if (result instanceof Collection)
			return ((Collection) result).isEmpty();
		else if (result instanceof Map)
			return ((Map) result).isEmpty();
		else if (result instanceof PageSet)
			return ((PageSet) result).getCount() == 0;
		else if (result.getClass().isArray())
			return Array.getLength(result) == 0;
		else
			return false;
	}

	/**
	 * <b>toResult。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ex 异常对象
	 * @return Result
	 */
	public static <R> Result<R> toResult(Exception ex) {
		return new Result<R>(ex.getClass().getName(), ex.getMessage());
	}

	/**
	 * <b>toResult。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param r 结果值
	 * @param ex 异常对象
	 * @return Result
	 */
	public static <R> Result<R> toResult(R r, Exception ex) {
		Result<R> result = new Result<R>(ex.getClass().getName(), ex.getMessage());
		result.setResult(r);
		return result;
	}

	/**
	 * <b>toResult。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param errorCode 错误代码
	 * @param errotText 错误文本
	 * @return Result
	 */
	public static <R> Result<R> toResult(String errorCode, String errotText) {
		return new Result<R>(errorCode, errotText);
	}

	/**
	 * <b>toResult。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param errorCode 错误代码
	 * @param errotText 错误文本
	 * @param cause 错误原因
	 * @return Result
	 */
	public static <R> Result<R> toResult(String errorCode, String errotText, Exception cause) {
		String errorCause = null;
		if (cause != null) {
			errorCause = cause.getClass().getName();
			if (!StringUtil.isBlank(cause.getMessage())) {
				errorCause += "," + cause.getMessage();
			}
		}
		return new Result<R>(errorCode, errotText, errorCause);
	}

	/**
	 * <b>toResult。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param result 结果值
	 * @return Result
	 */
	public static <R> Result<R> toResult(R result) {
		return new Result<R>(result);
	}

	/**
	 * <b>解析json字符串到JsonResult对象。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 如果不能解析或结果有错误则抛出异常。
	 * @param json json字符串
	 * @param type R类型
	 * @param parameterClasses 给出无法自动判断的泛型类型
	 * @return JsonResult对象
	 */
	public static <R> Result<R> toResult(String json, final Class<R> type, final Class<?>... parameterClasses)
			throws ResultException {
		if (!JsonUtil.isObject(json))
			throw new ParseException(json);
		Result<R> result = JsonUtil.fromJson(json, new Function<TypeFactory, JavaType>() {

			@Override
			public JavaType apply(TypeFactory typeFactory) {
				return ArrayUtils.isEmpty(parameterClasses) ? typeFactory.constructParametricType(Result.class, type)
						: typeFactory.constructParametricType(Result.class,
								typeFactory.constructParametricType(type, parameterClasses));
			}
		});
		if (result.isHasError())
			throw result.resultExcetion();
		return result;
	}

	/**
	 * <b>返回布尔类型的结果值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param r Boolean
	 * @return 布尔类型的结果值
	 */
	public static Result<Boolean> of(Boolean r) {
		return of(Boolean.TRUE.equals(r));
	}

	/**
	 * <b>返回布尔类型的结果值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param r boolean
	 * @return 布尔类型的结果值
	 */
	public static Result<Boolean> of(boolean r) {
		return r ? TRUE : FALSE;
	}

	private static class BooleanResult extends Result<Boolean> {

		public BooleanResult(Boolean result) {
			super(result);
		}

		@Override
		public void setResult(Boolean result) {
			throw new UnsupportException();
		}

		@Override
		public void setErrorCode(String errorCode) {
			throw new UnsupportException();
		}

		@Override
		public void setErrorText(String errorText) {
			throw new UnsupportException();
		}

		@Override
		public void setErrorCause(String errorCause) {
			throw new UnsupportException();
		}
	}
}
