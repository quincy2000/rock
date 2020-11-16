package org.quincy.rock.core.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.quincy.rock.core._Utils;
import org.quincy.rock.core.service.Service;
import org.quincy.rock.core.util.DaoUtil;
import org.quincy.rock.core.vo.BaseEntity;
import org.quincy.rock.core.vo.BaseVo;
import org.quincy.rock.core.vo.PageSet;
import org.quincy.rock.core.vo.Result;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <b>实现reset接口的MVC控制器基接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 该控制器使用SpringMVC技术，和Spring紧密结合。<br>
 * BaseRestController在BaseController基础上合并rock的DAO层和Service层实现机制，实现了数据表的常用操作(常用的增删查改)。
 * 开发者基于BaseRestController创建的控制器子类可以使用这些方法，而不需要自己去实现这些通用的数据表方法。<br>
 * 这些方法通常都返回使用Result封装的json格式数据，而输入参数则分为json对象版本和请求参数版本(对应XXX_json方法和XXX方法)<br>
 * 另外，大多数方法该提供了拦截方法(interceptXXX方法)，开发者可以重写该方法实现定制拦截。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月12日 上午9:29:21</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class BaseRestController<E extends BaseEntity<ID>, T extends BaseVo<E, ID>, ID>
		extends BaseController<E, T, ID> {

	/**
	 * <b>使用post方法提交保存新json对象数据(Body方式)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 一般用于保存新增记录。
	 * @param vo json对象数据
	 * @return Result<T>
	 */
	@RequestMapping(value = "/post_json", method = RequestMethod.POST)
	public final @ResponseBody Result<T> post_json(@RequestBody T vo) {
		return this.post(vo);
	}

	/**
	 * <b>使用post方法提交保存新业务值对象数据(请求参数方式)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 一般用于保存新增记录。
	 * @param vo 业务值对象数据
	 * @return Result<T>
	 */
	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public final @ResponseBody Result<T> post(T vo) {
		Service<E, ID> service = this.getService();
		try {
			interceptPost(vo);
			E entity = vo.entity();
			interceptPost(entity);
			entity = service.save(entity);
			vo.entity(entity);
			return Result.toResult(vo);
		} catch (DataIntegrityViolationException e) {
			String msg = _Utils.MSA.getMessage("controller.insert.constraint");
			return Result.toResult("post", msg, e);
		} catch (Exception e) {
			return Result.toResult("post", e.getMessage(), e);
		}
	}

	/**
	 * <b>拦截提交的值对象。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param vo 值对象
	 */
	protected void interceptPost(T vo) {

	}

	/**
	 * <b>提交实体前拦截。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param entity 实体
	 */
	protected void interceptPost(E entity) {

	}

	/**
	 * <b>同post_json方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param vo json对象数据
	 * @return Result<T>
	 */
	@RequestMapping(value = "/add_json", method = RequestMethod.POST)
	public final @ResponseBody Result<T> add_json(@RequestBody T vo) {
		return add(vo);
	}

	/**
	 * <b>同post方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param vo 业务值对象数据
	 * @return Result<T>
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public final @ResponseBody Result<T> add(T vo) {
		return post(vo);
	}

	/**
	 * <b>同post_json方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param vo json对象数据
	 * @return Result<T>
	 */
	@RequestMapping(value = "/append_json", method = RequestMethod.POST)
	public final @ResponseBody Result<T> append_json(@RequestBody T vo) {
		return append(vo);
	}

	/**
	 * <b>同post方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param vo 业务值对象数据
	 * @return Result<T>
	 */
	@RequestMapping(value = "/append", method = RequestMethod.POST)
	public final @ResponseBody Result<T> append(T vo) {
		return post(vo);
	}

	/**
	 * <b>同post_json方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param vo json对象数据
	 * @return Result<T>
	 */
	@RequestMapping(value = "/insert_json", method = RequestMethod.POST)
	public final @ResponseBody Result<T> insert_json(@RequestBody T vo) {
		return insert(vo);
	}

	/**
	 * <b>同post方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param vo 业务值对象数据
	 * @return Result<T>
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public final @ResponseBody Result<T> insert(T vo) {
		return post(vo);
	}

	/**
	 * <b>使用post或put方法提交保存json对象数据(Body方式)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 一般用于保存更新记录。
	 * @param vo json对象数据
	 * @return Result<T>
	 */
	@RequestMapping(value = "/put_json", method = { RequestMethod.POST, RequestMethod.PUT })
	public final @ResponseBody Result<T> put_json(@RequestBody T vo) {
		return this.put(vo);
	}

	/**
	 * <b>使用post或put方法提交保存业务值对象数据(请求参数方式)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 一般用于保存更新记录。
	 * @param vo 业务值对象数据
	 * @return Result<T>
	 */
	@RequestMapping(value = "/put", method = { RequestMethod.POST, RequestMethod.PUT })
	public final @ResponseBody Result<T> put(T vo) {
		Service<E, ID> service = this.getService();
		try {
			interceptPut(vo);
			E entity = vo.entity();
			interceptPut(entity);
			entity = service.update(entity);
			vo.entity(entity);
			return Result.toResult(vo);
		} catch (DataIntegrityViolationException e) {
			String msg = _Utils.MSA.getMessage("controller.update.constraint");
			return Result.toResult("put", msg, e);
		} catch (Exception e) {
			return Result.toResult("put", e.getMessage(), e);
		}
	}

	/**
	 * <b>拦截提交的值对象。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param vo 值对象
	 */
	protected void interceptPut(T vo) {

	}

	/**
	 * <b>更新实体前拦截。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param entity 实体
	 */
	protected void interceptPut(E entity) {

	}

	/**
	 * <b>同put_json方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param vo json对象数据
	 * @return Result<T>
	 */
	@RequestMapping(value = "/edit_json", method = { RequestMethod.POST, RequestMethod.PUT })
	public final @ResponseBody Result<T> edit_json(@RequestBody T vo) {
		return edit(vo);
	}

	/**
	 * <b>同put方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param vo 业务值对象数据
	 * @return Result<T>
	 */
	@RequestMapping(value = "/edit", method = { RequestMethod.POST, RequestMethod.PUT })
	public final @ResponseBody Result<T> edit(T vo) {
		return put(vo);
	}

	/**
	 * <b>同put_json方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param vo json对象数据
	 * @return Result<T>
	 */
	@RequestMapping(value = "/update_json", method = { RequestMethod.POST, RequestMethod.PUT })
	public final @ResponseBody Result<T> update_json(@RequestBody T vo) {
		return update(vo);
	}

	/**
	 * <b>同put方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param vo 业务值对象数据
	 * @return Result<T>
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST, RequestMethod.PUT })
	public final @ResponseBody Result<T> update(T vo) {
		return put(vo);
	}

	/**
	 * <b>使用post或put方法局部更新json对象数据(Body方式)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 一次更新一个或少量字段值。
	 * @param vo json对象数据
	 * @return Result<T>
	 */
	@RequestMapping(value = "/updateSpecific_json", method = { RequestMethod.POST, RequestMethod.PUT })
	public final @ResponseBody Result<T> updateSpecific_json(@RequestBody Map<String, Object> vo) {
		return this.updateSpecific(vo);
	}

	/**
	 * <b>使用post或put方法局部更新业务值对象数据(请求参数方式)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 一次更新一个或少量字段值。
	 * @param vo 业务值对象数据
	 * @return Result<T>
	 */
	@RequestMapping(value = "/updateSpecific", method = { RequestMethod.POST, RequestMethod.PUT })
	public final @ResponseBody Result<T> updateSpecific(@RequestParam Map<String, Object> vo) {
		Service<E, ID> service = this.getService();
		try {
			interceptUpdateSpecific(vo);
			ID id = toId(vo.get(ID_KEY).toString());
			E entity = service.updateSpecific(id, vo);
			return Result.toResult(wrap(entity));
		} catch (DataIntegrityViolationException e) {
			String msg = _Utils.MSA.getMessage("controller.update.constraint");
			return Result.toResult("updateSpecific", msg, e);
		} catch (Exception e) {
			return Result.toResult("updateSpecific", e.getMessage(), e);
		}
	}

	/**
	 * <b>拦截updateSpecific。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param vo 更新值
	 */
	protected void interceptUpdateSpecific(Map<String, Object> vo) {

	}

	/**
	 * <b>使用GET或DELETE方法删除指定记录。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 要删除记录的主键id，多个id之间使用逗号分隔
	 * @return 是否成功删除
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.DELETE })
	public final @ResponseBody Result<Boolean> delete(@RequestParam(name = ID_KEY) String id) {
		Service<E, ID> service = this.getService();
		try {
			List<ID> ids = toIds(id);
			interceptRemove(ids);
			boolean ok = true;
			if (ids.size() == 1) {
				ok = service.deleteById(ids.get(0)) != null;
			} else {
				service.deleteAllById(ids);
			}
			return Result.of(ok);
		} catch (DataIntegrityViolationException e) {
			String msg = _Utils.MSA.getMessage("controller.remove.constraint");
			return Result.toResult("delete", msg, e);
		} catch (Exception e) {
			return Result.toResult("delete", e.getMessage(), e);
		}
	}

	/**
	 * <b>删除实体前拦截。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ids 实体列表
	 */
	protected void interceptRemove(List<ID> ids) {

	}

	/**
	 * <b>同delete方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 要删除记录的主键id，多个id之间使用逗号分隔
	 * @return 是否成功删除
	 */
	@RequestMapping(value = "/remove", method = { RequestMethod.GET, RequestMethod.DELETE })
	public final @ResponseBody Result<Boolean> remove(@RequestParam(name = ID_KEY) String id) {
		return delete(id);
	}

	/**
	 * <b>查询单条记录。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 记录主键id
	 * @return Result<T>
	 */
	@RequestMapping(value = "/get", method = { RequestMethod.GET })
	public final @ResponseBody Result<T> get(@RequestParam(name = ID_KEY) ID id) {
		Service<E, ID> service = this.getService();
		try {
			E entity = service.findById(id, (e) -> interceptGet(e));
			return Result.toResult(wrap(entity));
		} catch (Exception e) {
			return Result.toResult("get", e.getMessage(), e);
		}
	}

	/**
	 * <b>拦截get方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param entity 实体
	 */
	protected void interceptGet(E entity) {

	}

	/**
	 * <b>根据主键id查询多条记录。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 多个id之间使用逗号分隔
	 * @return Result<List<T>>
	 */
	@RequestMapping(value = "/find", method = { RequestMethod.GET })
	public final @ResponseBody Result<List<T>> find(@RequestParam(name = ID_KEY) String id) {
		Service<E, ID> service = this.getService();
		try {
			List<ID> ids = toIds(id);
			List<E> list = (ids.size() == 1) ? Arrays.asList(service.findById(ids.get(0), (e) -> interceptFind(e)))
					: service.findAllById(ids, (e) -> interceptFind(e));
			return Result.toResult(wrap(list));
		} catch (Exception e) {
			return Result.toResult("find", e.getMessage(), e);
		}
	}

	/**
	 * <b>拦截find方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param entity 实体
	 */
	protected void interceptFind(E entity) {

	}

	/**
	 * <b>返回所有的记录。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param _order 排序规则，例如:age asc,sex desc
	 * @return Result<List<T>>
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public final @ResponseBody Result<List<T>> list(@RequestParam(name = ORDER_KEY, required = false) String _order) {
		Service<E, ID> service = this.getService();
		try {
			Sort sort = DaoUtil.toSort(_order);
			List<E> list = service.findAll(sort, (e) -> interceptList(e));
			return Result.toResult(wrap(list));
		} catch (Exception e) {
			return Result.toResult("list", e.getMessage(), e);
		}
	}

	/**
	 * <b>拦截list方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param entity 实体
	 */
	protected void interceptList(E entity) {

	}

	/**
	 * <b>分页查询数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param _page 页码(从0开始)
	 * @param _pageSize 页大小
	 * @param _order 排序规则，例如:age asc,sex desc 
	 * @return Result<PageSet<T>>
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public final @ResponseBody Result<PageSet<T>> page(@RequestParam(PAGE_KEY) Integer _page,
			@RequestParam(PAGE_SIZE_KEY) Integer _pageSize,
			@RequestParam(name = ORDER_KEY, required = false) String _order) {
		Service<E, ID> service = this.getService();
		try {
			Pageable pageable = PageRequest.of(_page, _pageSize, DaoUtil.toSort(_order));
			Page<E> page = service.findAll(pageable, (e) -> interceptPage(e));
			return Result.toResult(toPageSet(page));
		} catch (Exception e) {
			return Result.toResult("page", e.getMessage(), e);
		}
	}

	/**
	 * <b>拦截page方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param entity 实体
	 */
	protected void interceptPage(E entity) {

	}

	/**
	 * <b>返回指定的数据是否存在。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 只要有一个id存在即返回true。
	 * @param id 主键id，多个id之间使用逗号分隔
	 * @return 指定的数据是否存在
	 */
	@RequestMapping(value = "/exists", method = { RequestMethod.GET })
	public final @ResponseBody Result<Boolean> exists(@RequestParam(name = ID_KEY) String id) {
		Service<E, ID> service = this.getService();
		try {
			List<ID> ids = toIds(id);
			interceptExists(ids);
			boolean ok = ids.size() == 1 ? service.existsById(ids.get(0)) : service.existsById(ids);
			return Result.of(ok);
		} catch (Exception e) {
			return Result.toResult("exists", e.getMessage(), e);
		}
	}

	/**
	 * <b>返回指定的数据是否全部存在。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 主键id，多个id之间使用逗号分隔
	 * @return 指定的数据是否全部存在
	 */
	@RequestMapping(value = "/existsAll", method = { RequestMethod.GET })
	public final @ResponseBody Result<Boolean> existsAll(@RequestParam(name = ID_KEY) String id) {
		Service<E, ID> service = this.getService();
		try {
			List<ID> ids = toIds(id);
			interceptExists(ids);
			boolean ok = ids.size() == 1 ? service.existsById(ids.get(0)) : service.existsAllById(ids);
			return Result.of(ok);
		} catch (Exception e) {
			return Result.toResult("existsAll", e.getMessage(), e);
		}
	}

	/**
	 * <b>拦截exists和existsAll方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ids 实体列表
	 */
	protected void interceptExists(List<ID> ids) {

	}
}
