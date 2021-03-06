package com.integrate.admin.controller.app;

import com.integrate.admin.controller.base.BaseController;
import com.integrate.admin.module.app.model.AppInfo;
import com.integrate.admin.module.app.service.*;
import com.integrate.admin.util.AppUtil;
import com.integrate.admin.util.Const;
import com.integrate.admin.util.FileUpload;
import com.integrate.admin.util.PageData;
import com.integrate.core.qiniu.QiniuService;
import com.integrate.exception.BusinessException;
import com.integrate.model.*;
import com.integrate.url.UrlCommand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class AppAdminController extends BaseController {

    @Autowired
    private QiniuService qiniuService;
    @Autowired
    private AppService appService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private HotKeyService hotKeyService;

    @RequestMapping(value = UrlCommand.app)
    public ModelAndView getProportion() {

        ModelAndView mv = new ModelAndView();

        AppInfo appinfo = appService.getAppinfo();
        mv.setViewName("app/app");
        mv.addObject("p", appinfo);
        mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
        return mv;
    }

    /**
     *
     */
    @RequestMapping(value = UrlCommand.app_goto_edit)
    public ModelAndView goAdd(HttpServletRequest request) {
        ModelAndView mv = this.getModelAndView();
        String version = request.getParameter("version");

        mv.addObject("version", version);
        mv.setViewName("app/edit");

        return mv;
    }

    @RequestMapping(value = UrlCommand.app_edit)
    public ModelAndView update(HttpServletRequest request, @RequestParam(value = "tp", required = false) MultipartFile file) {

        String version = request.getParameter("version");
        String description = request.getParameter("description");
        String url = null;
        if (file != null) {
            /*CommonsMultipartFile cf = (CommonsMultipartFile) file;
            DiskFileItem fi = (DiskFileItem) cf.getFileItem();
            File f = fi.getStoreLocation();
            String resourceKey = qiniuService.uploadFile(f, QiniuBucket.HEAD_IMG.getBucket());
            String title = file.getOriginalFilename();
            if (resourceKey != null) {
                url = QiniuBucket.HEAD_IMG.getDomain() + resourceKey + "?v=1" + "&attname=" + title;
            }*/

            String filePath = request.getSession().getServletContext().getRealPath("/") + "/" + Const.FILE_PATHF_APP;                                //文件上传路径
            String fileName = new StringBuffer("yimencheng.v").append(version).toString();
            fileName = FileUpload.fileUp(file, filePath, fileName);

            url = new StringBuilder("http://")
                    .append(request.getServerName())
                    .append(":")
                    .append(request.getServerPort())
                    .append(request.getContextPath())
                    .append("/")
                    .append(Const.FILE_PATHF_APP)
                    .append(fileName)
                    .toString();
        }

        appService.update(url, version, description);
        ModelAndView mv = this.getModelAndView();
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    @RequestMapping(value = UrlCommand.image_new)
    public ModelAndView getImageNews(HttpServletRequest request) {
        ModelAndView mv = this.getModelAndView();
        try {
            List<Image> images = imageService.getImages(0L);
            mv.addObject("images", images);
            mv.setViewName("app/image_news");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

        return mv;
    }

    @RequestMapping(value = UrlCommand.image_upload)
    public ModelAndView uploadImage(HttpServletRequest request, @RequestParam(value = "image", required = false) MultipartFile file) {

        String rid = request.getParameter("rid");
        ModelAndView mv = this.getModelAndView();
        try {
            Image image = new Image();
            if (file != null) {
                CommonsMultipartFile cf = (CommonsMultipartFile) file;
//                DiskFileItem fi = (DiskFileItem) cf.getFileItem();
//                File f = fi.getStoreLocation();

                //String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG;								//文件上传路径
                String filePath = request.getSession().getServletContext().getRealPath("/") + "/" + Const.FILEPATHIMG;                                //文件上传路径
//                String fileName = FileUpload.fileUp(file, filePath, fi.getName());
                String fileName = FileUpload.fileUp(file, filePath, UUID.randomUUID().toString());


                String url = new StringBuilder("http://")
                        .append(request.getServerName())
                        .append(":")
                        .append(request.getServerPort())
                        .append(request.getContextPath())
                        .append("/")
                        .append(Const.FILEPATHIMG)
                        .append(fileName)
                        .toString();


                image.setSrc(url);
                image.setTitle(fileName);
            }

            if (StringUtils.isBlank(rid)) {
                rid = "0";
            }
            image.setRid(Long.valueOf(rid));
            if (StringUtils.isNotBlank(image.getSrc())) {
                imageService.insertImage(image);
            }
            List<Image> images = imageService.getImages(0L);
            mv.addObject("images", images);
            mv.setViewName("app/image_news");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;

    }

    @RequestMapping(value = UrlCommand.image_thumbnail_upload, produces = "application/json;charset=UTF-8")
    public Object uploadImageThumbnail(HttpServletRequest request, @RequestParam(value = "image", required = false) MultipartFile file) {

        String rid = request.getParameter("rid");

        try {
            if (file != null && StringUtils.isNotBlank(rid)) {
                Product product = productService.getProduct(Long.valueOf(rid));
                if (product != null) {
                    CommonsMultipartFile cf = (CommonsMultipartFile) file;
//                    DiskFileItem fi = (DiskFileItem) cf.getFileItem();
//                    File f = fi.getStoreLocation();

                    //String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG;								//文件上传路径
                    String filePath = request.getSession().getServletContext().getRealPath("/") + "/" + Const.FILEPATHIMG;                                //文件上传路径
//                    String fileName = FileUpload.fileUp(file, filePath, fi.getName());
                    String fileName = FileUpload.fileUp(file, filePath, UUID.randomUUID().toString());


                    String url = new StringBuilder("http://")
                            .append(request.getServerName())
                            .append(":")
                            .append(request.getServerPort())
                            .append(request.getContextPath())
                            .append("/")
                            .append(Const.FILEPATHIMG)
                            .append(fileName)
                            .toString();
                    product.setThumbnail(url);
                    productService.updateProduct(product);

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        ModelAndView mv = this.getModelAndView();
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;

    }

    @RequestMapping(value = UrlCommand.image_thumbnail_delete, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteImageThumbnail(HttpServletRequest request) {

        String id = request.getParameter("id");

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", 200);
        result.put("msg", "删除失败！");
        try {
            if (StringUtils.isNotBlank(id)) {
                Product product = productService.getProduct(Long.valueOf(id));
                if (product != null) {

                    product.setThumbnail(null);
                    productService.updateProduct(product);

                    result.put("msg", "删除成功！");
                }

            }

        } catch (Exception e) {
            logger.error(e.toString(), e);
            if (e instanceof BusinessException) {
                BusinessException be = (BusinessException) e;
                result.put("msg", be.getMessage());
            }
        }
        return AppUtil.returnObject(new PageData(), result);

    }

    @RequestMapping(value = UrlCommand.image_delete, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteImage(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", 200);
        result.put("msg", "删除失败！");
        try {
            String id = request.getParameter("id");
            if (StringUtils.isNotBlank(id)) {
                int count = imageService.deleteImage(Long.valueOf(id));
                if (count == 1) {
                    result.put("msg", "删除成功");
                }
            }

        } catch (Exception e) {
            logger.error(e.toString(), e);
            if (e instanceof BusinessException) {
                BusinessException be = (BusinessException) e;
                result.put("msg", be.getMessage());
            }
        }
        return AppUtil.returnObject(new PageData(), result);
    }

    @RequestMapping(value = UrlCommand.article_new)
    public ModelAndView getArticleNews(HttpServletRequest request) {
        ModelAndView mv = this.getModelAndView();
        try {
            List<Article> articles = articleService.getArticles();
            mv.addObject("articles", articles);
            mv.setViewName("app/article_news");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

        return mv;
    }

    @RequestMapping(value = UrlCommand.article_new_goto_edit)
    public ModelAndView editArticleNew(HttpServletRequest request) {
        ModelAndView mv = this.getModelAndView();
        try {
            String id = request.getParameter("id");
            if (StringUtils.isNotBlank(id)) {
                Article article = articleService.getArticle(Long.valueOf(id));
                mv.addObject("article", article);
            }
            mv.setViewName("app/article_edit");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value = UrlCommand.article_new_goto_view)
    public ModelAndView viewArticleNew(HttpServletRequest request) {
        ModelAndView mv = this.getModelAndView();
        try {
            String id = request.getParameter("id");
            if (StringUtils.isNotBlank(id)) {
                Article article = articleService.getArticle(Long.valueOf(id));
                mv.addObject("article", article);
            }
            mv.setViewName("app/article_view");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value = UrlCommand.article_new_edit)
    public ModelAndView saveArticleNew(HttpServletRequest request) {
        ModelAndView mv = this.getModelAndView();
        try {
            String id = request.getParameter("id");
            String title = request.getParameter("title");
            String dateTime = request.getParameter("dateTime");
            String detail = request.getParameter("detail");
            if (StringUtils.isNotBlank(title)
                    && StringUtils.isNotBlank(detail)
                    && StringUtils.isNotBlank(dateTime)) {
                long now = System.currentTimeMillis();
                Article article = new Article();
                article.setTitle(title);
                article.setDateTime(dateTime);
                article.setDetail(detail);
                article.setUpdateTime(now);

                if (StringUtils.isBlank(id)) {
                    article.setCreateTime(now);
                    articleService.insertArticle(article);
                } else {
                    article.setId(Long.valueOf(id));
                    articleService.updateArticle(article);
                }
            }

            List<Article> articles = articleService.getArticles();
            mv.addObject("articles", articles);
            mv.setViewName("app/article_news");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

        return mv;
    }

    @RequestMapping(value = UrlCommand.article_new_delete, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteArticleNew(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", 200);
        result.put("msg", "删除失败！");
        try {
            String id = request.getParameter("id");
            if (StringUtils.isNotBlank(id)) {
                int count = articleService.deleteArticle(Long.valueOf(id));
                if (count == 1) {
                    result.put("msg", "删除成功");
                }
            }

        } catch (Exception e) {
            logger.error(e.toString(), e);
            if (e instanceof BusinessException) {
                BusinessException be = (BusinessException) e;
                result.put("msg", be.getMessage());
            }
        }
        return AppUtil.returnObject(new PageData(), result);
    }

    @RequestMapping(value = UrlCommand.product_list)
    public ModelAndView getProducts(HttpServletRequest request) {
        ModelAndView mv = this.getModelAndView();
        try {
            List<Product> products = productService.getProducts();
            mv.addObject("products", products);
            mv.setViewName("app/product_list");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

        return mv;
    }

    @RequestMapping(value = UrlCommand.product_goto_edit)
    public ModelAndView editProduct(HttpServletRequest request) {
        ModelAndView mv = this.getModelAndView();
        try {
            String id = request.getParameter("id");
            if (StringUtils.isNotBlank(id)) {
                Product product = productService.getProduct(Long.valueOf(id));
                mv.addObject("product", product);
                mv.setViewName("app/product_edit");
            } else {
                mv.setViewName("app/product_add");
            }
            List<Category> categorys = categoryService.getCategorys();
            mv.addObject("categorys", categorys);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value = UrlCommand.product_edit)
    public ModelAndView updateProduct(HttpServletRequest request, @ModelAttribute Product product) {
        ModelAndView mv = this.getModelAndView();
        try {
            String id = request.getParameter("id");
            if (StringUtils.isNotBlank(id)) {

                product.setUpdateTime(System.currentTimeMillis());
                productService.updateProduct(product);

                List<Product> products = productService.getProducts();
                mv.addObject("products", products);
                mv.setViewName("app/product_list");

            } else {

                product.setCreateTime(System.currentTimeMillis());
                product.setUpdateTime(product.getCreateTime());
                productService.insertProduct(product);
                mv.addObject("msg", "success");
                mv.setViewName("save_result");
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value = UrlCommand.product_delete, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteProduct(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", 200);
        result.put("msg", "删除失败！");
        try {
            String id = request.getParameter("id");
            if (StringUtils.isNotBlank(id)) {
                int count = productService.deleteProduct(Long.valueOf(id));
                if (count == 1) {
                    result.put("msg", "删除成功");
                }
            }

        } catch (Exception e) {
            logger.error(e.toString(), e);
            if (e instanceof BusinessException) {
                BusinessException be = (BusinessException) e;
                result.put("msg", be.getMessage());
            }
        }
        return AppUtil.returnObject(new PageData(), result);
    }

    @RequestMapping(value = UrlCommand.hot_key)
    public ModelAndView getHotKeys() {
        ModelAndView mv = this.getModelAndView();
        try {
            List<HotKey> keys = hotKeyService.getHotKeys();
            mv.addObject("hotKeys", keys);
            mv.setViewName("app/hot_key_list");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

        return mv;
    }

    @RequestMapping(value = UrlCommand.hot_key_goto_edit)
    public ModelAndView editHotKey(HttpServletRequest request) {
        ModelAndView mv = this.getModelAndView();
        try {
            String id = request.getParameter("id");
            if (StringUtils.isNotBlank(id)) {
                HotKey key = hotKeyService.getHotKey(Long.valueOf(id));
                mv.addObject("hotKey", key);
            }
            mv.setViewName("app/hot_key_edit");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value = UrlCommand.hot_key_edit)
    public ModelAndView updateHotKey(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            String keyWord = request.getParameter("keyWord");
            if (StringUtils.isNotBlank(keyWord)) {
                long now = System.currentTimeMillis();
                HotKey key = new HotKey();
                key.setKeyWord(keyWord);
                key.setUpdateTime(now);

                if (StringUtils.isBlank(id)) {
                    key.setCreateTime(now);
                    hotKeyService.insertHotKey(key);
                } else {
                    key.setId(Long.valueOf(id));
                    hotKeyService.updateHotKey(key);
                }
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        ModelAndView mv = this.getModelAndView();
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    @RequestMapping(value = UrlCommand.hot_key_delete, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteHotKey(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", 200);
        result.put("msg", "删除失败！");
        try {
            String id = request.getParameter("id");
            if (StringUtils.isNotBlank(id)) {
                int count = hotKeyService.deleteHotKey(Long.valueOf(id));
                if (count == 1) {
                    result.put("msg", "删除成功");
                }
            }

        } catch (Exception e) {
            logger.error(e.toString(), e);
            if (e instanceof BusinessException) {
                BusinessException be = (BusinessException) e;
                result.put("msg", be.getMessage());
            }
        }
        return AppUtil.returnObject(new PageData(), result);
    }

    @RequestMapping(value = UrlCommand.category_list)
    public ModelAndView getCategories(HttpServletRequest request) {
        ModelAndView mv = this.getModelAndView();
        try {
            List<Category> categorys = categoryService.getCategorys();
            mv.addObject("categorys", categorys);
            mv.setViewName("app/category_list");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

        return mv;
    }

    @RequestMapping(value = UrlCommand.category_goto_edit)
    public ModelAndView editCategory(HttpServletRequest request) {
        ModelAndView mv = this.getModelAndView();
        try {
            String id = request.getParameter("id");
            if (StringUtils.isNotBlank(id)) {
                Category category = categoryService.getCategory(Long.valueOf(id));
                mv.addObject("category", category);
            }
            mv.setViewName("app/category_edit");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value = UrlCommand.category_edit)
    public ModelAndView updateCategory(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            String categoryName = request.getParameter("categoryName");
            if (StringUtils.isNotBlank(categoryName)) {
                long now = System.currentTimeMillis();
                Category category = new Category();
                category.setCategoryName(categoryName);
                category.setUpdateTime(now);

                if (StringUtils.isBlank(id)) {
                    category.setCreateTime(now);
                    categoryService.insertCategory(category);
                } else {
                    category.setId(Long.valueOf(id));
                    categoryService.updateCategory(category);
                }
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        ModelAndView mv = this.getModelAndView();
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    @RequestMapping(value = UrlCommand.category_delete, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteCategory(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", 200);
        result.put("msg", "删除失败！");
        try {
            String id = request.getParameter("id");
            if (StringUtils.isNotBlank(id)) {
                int count = categoryService.deleteCategory(Long.valueOf(id));
                if (count == 1) {
                    result.put("msg", "删除成功");
                }
            }

        } catch (Exception e) {
            logger.error(e.toString(), e);
            if (e instanceof BusinessException) {
                BusinessException be = (BusinessException) e;
                result.put("msg", be.getMessage());
            }
        }
        return AppUtil.returnObject(new PageData(), result);
    }

    @RequestMapping(value = UrlCommand.mobile_coupon_list)
    public ModelAndView getMobileCoupons(HttpServletRequest request) {
        ModelAndView mv = this.getModelAndView();
        try {
            List<Coupon> coupons = couponService.getCoupons();
            mv.addObject("coupons", coupons);
            mv.setViewName("app/coupon_list");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

        return mv;
    }

    @RequestMapping(value = UrlCommand.mobile_coupon_goto_edit)
    public ModelAndView editMobileCoupon(HttpServletRequest request) {
        ModelAndView mv = this.getModelAndView();
        try {
            String id = request.getParameter("id");
            if (StringUtils.isNotBlank(id)) {
                Coupon coupon = couponService.getCoupon(Long.valueOf(id));
                mv.addObject("coupon", coupon);
            }
            mv.setViewName("app/coupon_edit");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value = UrlCommand.mobile_coupon_edit)
    public ModelAndView updateMobileCoupon(HttpServletRequest request, @ModelAttribute Coupon coupon) {
        try {
            String id = request.getParameter("id");
            coupon.setType(1);
            if (StringUtils.isBlank(id)) {
                couponService.insertCoupon(coupon);
            } else {
                couponService.updateCoupon(coupon);
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        ModelAndView mv = this.getModelAndView();
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = UrlCommand.mobile_coupon_delete, produces = "application/json;charset=UTF-8")
    public Object deleteMobileCoupon(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", 200);
        result.put("msg", "删除失败！");
        try {
            String id = request.getParameter("id");
            if (StringUtils.isNotBlank(id)) {
                int count = couponService.deleteCoupon(Long.valueOf(id));
                if (count == 1) {
                    result.put("msg", "删除成功");
                }
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
            if (e instanceof BusinessException) {
                BusinessException be = (BusinessException) e;
                result.put("msg", be.getMessage());
            }
        }
        return AppUtil.returnObject(new PageData(), result);
    }

}
