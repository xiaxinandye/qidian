package com.yunche.novels.controller;

import com.yunche.novels.bean.BookShelf;
import com.yunche.novels.bean.BookShelfShow;
import com.yunche.novels.bean.Novel;
import com.yunche.novels.bean.User;
import com.yunche.novels.service.ChapterService;
import com.yunche.novels.service.HomeService;
import com.yunche.novels.service.NovelService;
import com.yunche.novels.service.UserService;
import com.yunche.novels.service.impl.ChapterServiceImpl;
import com.yunche.novels.service.impl.HomeServiceImpl;
import com.yunche.novels.service.impl.NovelServiceImpl;
import com.yunche.novels.service.impl.UserServiceImpl;
import com.yunche.novels.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author yunche
 * @date 2019/04/06
 */
@Controller
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    public HomeServiceImpl homeService;
    @Autowired
    private NovelServiceImpl novelService;
    @Autowired
    private ChapterServiceImpl chapterService;

    /**
     * 自动更新读者的书架中的书籍的阅读进度
     *
     * @param userName 用户名
     * @param nid      小说id
     * @param lastCid  最新阅读章节id
     * @return
     */
    @ResponseBody
    @GetMapping("/bookshelf/{uname}/{nid}/{cid}")
    public String bookshelf(@PathVariable("uname") String userName, @PathVariable("nid") String nid, @PathVariable("cid") String lastCid) {

        if (userService.isExistNidInBookShelf(userName, nid)) {
            Date date = new Date();
            Timestamp readTime = new Timestamp(date.getTime());
            userService.updateLastReadInBookShelf(userName, nid, lastCid, readTime);
            return "updated";
        }
        return "nothing";
    }

    @ResponseBody
    @GetMapping("/user/{name}")
    public String getUserName(@PathVariable("name") String name) {
        return userService.IsExistsName(name) ? "yes" : "no";
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String name, @RequestParam("password") String password, @RequestParam("ack-password") String ackPassword) {
        //暂未考虑并发时用户名唯一的问题
        if (!password.equals(ackPassword)) {
            return "error";
        }
        User u = new User();
        u.setUserName(name);
        u.setPassword(password);
        return userService.insertUser(u) ? "user/success" : "user/fail";
    }

    @RequestMapping("/userlogin")
    public String login(String username, String password, Map<String, Object> map, HttpSession session) {
        if (userService.validate(username, MD5Utils.MD5Encode(password, "utf-8", true))) {
            session.setAttribute("user", username);
            return "redirect:/index";
        }
        List<Novel> top10TM = homeService.getTop10ByTM();
        List<Novel> top10RM = homeService.getTop10ByRM();
        map.put("tm", top10TM);
        map.put("rm", top10RM);
        map.put("loginState", "fail");
        return "/index";
    }

    @GetMapping("/success")
    public String loginSuccess(HttpSession session) {
        session.setAttribute("state", "success");
        return "redirect:/index";
    }

    @GetMapping("/login/error")
    public String loginError(HttpSession session) {
        session.setAttribute("state", "fail");
        return "redirect:/index";
    }

    @GetMapping("/bookshelf/{nid}")
    public String addBookShelf(@PathVariable("nid") String nid, HttpSession session, Principal principal) throws UnsupportedEncodingException {
        if (session.getAttribute("user") != null) {
            String username = (String) session.getAttribute("user");
            String startChapterId = novelService.getStartChapterId(nid);
            userService.insertBookToShelf(username, nid, startChapterId);
            return "redirect:/bookshelf/" + URLEncoder.encode(username, "UTF-8")+ "/all";
        } else if (principal.getName() != null) {
            String username = principal.getName();
            String startChapterId = novelService.getStartChapterId(nid);
            userService.insertBookToShelf(username, nid, startChapterId);
            return "redirect:/bookshelf/" + URLEncoder.encode(username, "UTF-8")+ "/all";
        } else {
            return "Forbidden";
        }
    }

    @GetMapping("/bookshelf/remove/{nid}")
    public String removeBookFromShelf(@PathVariable("nid") String nid, HttpSession session, Principal principal) {
        if (session.getAttribute("user") != null) {
            String username = (String) session.getAttribute("user");
            userService.removeBookFromShelf(username, nid);
            return "redirect:/bookshelf/" + username + "/all";
        } else if (principal.getName() != null) {
            String username = principal.getName();
            userService.removeBookFromShelf(username, nid);
            return "redirect:/bookshelf/" + username + "/all";
        } else {
            return "Forbidden";
        }
    }

    @GetMapping("/bookshelf/{uname}/all")
    public String allBookShelfOfUser(@PathVariable("uname") String uname, Map<String, Object> map) {
        List<BookShelf> bookShelfList = userService.getBookShelf(uname);
        List<BookShelfShow> bookShelfShowList = new ArrayList<>();
        for(BookShelf bookShelf : bookShelfList) {
            String bookId = bookShelf.getNovelId();
            Novel novel = novelService.getNovelById(bookId);
            BookShelfShow bookShelfShow = new BookShelfShow();
            bookShelfShow.setBookName(novel.getName());
            bookShelfShow.setImageUrl(novel.getCoverImage());
            bookShelfShow.setLastReadTime(bookShelf.getReadTime());
            bookShelfShow.setLastChapterName(chapterService.getContentById(bookShelf.getchapterId()).getName());
            bookShelfShow.setNovelId(bookId);
            bookShelfShow.setChapterId(bookShelf.getchapterId());

            bookShelfShowList.add(bookShelfShow);
        }
        map.put("bookShelfShowList", bookShelfShowList);
        return "user/login/bookshelf";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, Principal principal, HttpServletRequest request, HttpServletResponse response) {

        if(session.getAttribute("user")!=null) {
            session.removeAttribute("user");
        } else if(principal.getName() != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null){
                Cookie cookie = new Cookie("remember-me", null);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
        }
        return "redirect:/index";
    }
}

