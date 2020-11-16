package org.quincy.rock.sso.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <b>验证码图片Servlet。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 产生一个随机验证码图片，并把验证码放在会话中。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年10月15日 下午2:21:56</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class CaptchaHttpServlet extends AbstractHttpServlet {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -4182157582451982448L;

	/**
	 * random。
	 */
	private final Random random = new Random();

	/**
	 * 存放在会话中的验证码的变量名。
	 */
	private String captchaName = "ssoCaptcha";
	/**
	 * 验证码字符数量。
	 */
	private int charCount = 4;
	/**
	 * 验证码字符字典。
	 */
	private String charDict = "0123456789";
	/**
	 * 干扰线数量。
	 */
	private int lineCount = 40;
	/**
	 * 图片宽度。
	 */
	private int imgWidth = 95;
	/**
	 * 图片高度。
	 */
	private int imgHeight = 25;
	/**
	 * 字体大小。
	 */
	private int fontSize = 18;

	/**
	 * <b>设置存放在会话中的验证码的变量名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param captchaName 存放在会话中的验证码的变量名
	 */
	public void setCaptchaName(String captchaName) {
		this.captchaName = captchaName;
	}

	/**
	 * <b>设置验证码字符数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param charCount 验证码字符数量
	 */
	public void setCharCount(int charCount) {
		this.charCount = charCount;
	}

	/**
	 * <b>设置验证码字符字典。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param charDict 验证码字符字典
	 */
	public void setCharDict(String charDict) {
		this.charDict = charDict;
	}

	/**
	 * <b>设置干扰线数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param lineCount 干扰线数量
	 */
	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}

	/**
	 * <b>设置图片宽度。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param imgWidth 图片宽度
	 */
	public void setImgWidth(int imgWidth) {
		this.imgWidth = imgWidth;
	}

	/**
	 * <b>设置图片高度。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param imgHeight 图片高度
	 */
	public void setImgHeight(int imgHeight) {
		this.imgHeight = imgHeight;
	}

	/**
	 * <b>设置字体大小。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param fontSize 字体大小
	 */
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	/** 
	 * execute。
	 * @see org.quincy.rock.sso.web.AbstractHttpServlet#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		resp.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
		resp.setHeader("Pragma", "no-cache");//设置响应头信息，告诉浏览器不要缓存此内容
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expire", 0);
		HttpSession session = req.getSession();
		BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();
		g.fillRect(0, 0, imgWidth, imgHeight);//图片大小
		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));//字体大小		
		//绘制干扰线
		for (int i = 0; i <= lineCount; i++) {
			int x = random.nextInt(imgWidth);
			int y = random.nextInt(imgHeight);
			int xl = random.nextInt(13);
			int yl = random.nextInt(15);
			g.setColor(getRandLineColor());
			g.drawLine(x, y, x + xl, y + yl);
		}
		//绘制字符串
		g.setFont(new Font("Fixedsys", Font.CENTER_BASELINE, fontSize));
		StringBuilder captcha = new StringBuilder(charCount);
		for (int i = 0; i < charCount; i++) {
			char c = getRandChar();
			captcha.append(c);
			g.setColor(getRandCharColor());
			g.translate(random.nextInt(3), random.nextInt(3));
			g.drawString(String.valueOf(c), 13 * i, 16);
		}
		g.dispose();
		//将生成的随机字符串保存到session中
		session.setAttribute(captchaName, captcha.toString());
		//将内存中的图片通过流动形式输出到客户端
		ImageIO.write(image, "JPEG", resp.getOutputStream());
	}

	/**
	 * 获得干扰线随机颜色
	 */
	private Color getRandLineColor() {
		int fc = 110, bc = 133;
		int r = fc + random.nextInt(bc - fc - 16);
		int g = fc + random.nextInt(bc - fc - 14);
		int b = fc + random.nextInt(bc - fc - 18);
		return new Color(r, g, b);
	}

	/**
	 * 获得字符随机颜色
	 */
	private Color getRandCharColor() {
		return new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121));
	}

	/**
	 * 获得随机字符
	 */
	private char getRandChar() {
		return charDict.charAt(random.nextInt(charDict.length()));
	}
}
