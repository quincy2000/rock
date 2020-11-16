package org.quincy.rock.comm.communicate;

import java.util.List;

import org.quincy.rock.core.vo.PageSet;
import org.quincy.rock.core.vo.Vo;

/**
 * <b>终端设备信息提供者接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 开发者实现该接口提供合法设备信息列表。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年5月29日 上午9:39:56</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface DeviceProvider<ID> {
	/**
	 * 设备信息
	 */
	public static abstract class DeviceInfo<ID> extends Vo<ID> {
		/**
		 * serialVersionUID。
		 */
		private static final long serialVersionUID = -1523230891896521543L;

		/**
		 * 唯一设备代码。
		 */
		protected String code;
		/**
		 * 设备类型代码。
		 */
		protected String typeCode;
		/**
		 * 设备类型名称。
		 */
		protected String typeName;

		/**
		 * <b>获得id。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @return id
		 */
		public final ID getId() {
			return id();
		}

		/**
		 * <b>设置id。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @param id id
		 */
		public final void setId(ID id) {
			this.id(id);
		}

		/**
		 * <b>获得唯一设备代码。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @return 唯一设备代码
		 */
		public final String getCode() {
			return code;
		}

		/**
		 * <b>设置唯一设备代码。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @param code 唯一设备代码
		 */
		public final void setCode(String code) {
			this.code = code;
		}

		/**
		 * <b>获得设备类型代码。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @return 设备类型代码
		 */
		public final String getTypeCode() {
			return typeCode;
		}

		/**
		 * <b>设置设备类型代码。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @param typeCode 设备类型代码
		 */
		public final void setTypeCode(String typeCode) {
			this.typeCode = typeCode;
		}

		/**
		 * <b>获得设备类型名称。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @return 设备类型名称
		 */
		public final String getTypeName() {
			return typeName;
		}

		/**
		 * <b>设置设备类型名称。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @param typeName 设备类型名称
		 */
		public final void setTypeName(String typeName) {
			this.typeName = typeName;
		}
	}

	/**
	 * <b>返回所有的设备类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 设备类型格式:代码|名称。
	 * @return 设备类型列表
	 */
	public List<String> getAllDeviceType();

	/**
	 * <b>查询单个设备。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 唯一设备代码
	 * @return 设备信息
	 */
	public <D extends DeviceInfo<ID>> D findDevice(Object code);

	/**
	 * <b>查询符合条件的所有的设备信息列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 设备类型,如果为空则返回所有设备
	 * @return 设备信息列表
	 */
	public <D extends DeviceInfo<ID>> List<D> findAllDevice(Object type);

	/**
	 * <b>查询符合条件的所有的设备信息列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 支持条件查询。
	 * @param type 设备类型,如果为空则查询所有
	 * @param code 设备代码,支持like查询(例如:%01%),如果为空则查询所有
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 设备信息列表
	 */
	public <D extends DeviceInfo<ID>> PageSet<D> findAllDevice(String type, String code, int page, int pageSize);
}
