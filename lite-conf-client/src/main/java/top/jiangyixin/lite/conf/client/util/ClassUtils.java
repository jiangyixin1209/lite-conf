package top.jiangyixin.lite.conf.client.util;

import java.io.File;
import java.io.FilenameFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author jiangyixin
 * @version 1.0
 * @date 2021/7/16 上午10:13
 */
@Slf4j
public class ClassUtils {
    
    /**
     * 扫描包路径下所有的class文件
     * @param pkg 包名
     * @return class集合
     */
    public static Set<Class<?>> scanClassFromPackagePath(String pkg) {
        Set<Class<?>> classes = new LinkedHashSet<>();
        String pkgDirName = pkg.replace(".",  File.separator);
        try{
            Enumeration<URL> urls = ClassUtils.class.getClassLoader().getResources(pkgDirName);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    scanClassesByFile(pkg, filePath, classes);
                } else if ("jar".equals(protocol)) {
                    JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                    scanClassesByJar(pkg, jar, classes);
                }
            }
        } catch (Exception e) {
            log.error("扫描包路径{}下class文件异常", pkg, e);
        }
        return classes;
    }
    
    /**
     * 扫描包路径下的所有class文件
     * @param pkgName 包名
     * @param pkgPath 包路径
     * @param classes 保存class集合
     */
    public static void scanClassesByFile(String pkgName, String pkgPath, Set<Class<?>> classes) {
        File dir = new File(pkgPath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 过滤掉非 class文件或者非目录文件
        File[] dirFiles = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return dir.isDirectory() || name.endsWith("class");
            }
        });
        if (dirFiles == null || dirFiles.length == 0) {
            return;
        }
        String className;
        Class<?> cls;
        for (File f: dirFiles) {
            if (f.isDirectory()) {
                scanClassesByFile(
                        pkgName.concat(".").concat(f.getName()),
                        pkgPath.concat("/").concat(f.getName()),
                        classes);
                continue;
            }
            className = f.getName();
            // 获取类名，去除.class后缀
            className = className.substring(0, className.length() - 6);
            cls = loadClass(className);
            if (cls != null) {
                classes.add(cls);
            }
        }
    }
    
    /**
     * 扫描包路径下的所有class文件
     * @param pkgName 包名
     * @param jar jar文件
     * @param classes 保存class集合
     */
    public static void scanClassesByJar(String pkgName, JarFile jar, Set<Class<?>> classes) {
        String dir = pkgName.replace(".",  File.separator);
        Enumeration<JarEntry> entries = jar.entries();
        
        JarEntry jarEntry;
        String className;
        String name;
        Class<?> cls;
        while (entries.hasMoreElements()) {
            jarEntry = entries.nextElement();
            name = jarEntry.getName();
            if (name.charAt(0) == '/') {
                name = name.substring(1);
            }
            // 排除非指定的包路径，非class文件
            if (jarEntry.isDirectory() || !name.startsWith(dir) || !name.endsWith(".class")) {
                continue;
            }
            className = name.substring(0, name.length() - 6);
            cls = loadClass(className.replace(File.separator, "."));
            if (cls != null) {
                classes.add(cls);
            }
        }
    }
    
    private static Class<?> loadClass(String fullClassName) {
        try{
            return Thread.currentThread().getContextClassLoader().loadClass(fullClassName);
        } catch (ClassNotFoundException e) {
            log.error("加载类{}失败", fullClassName);
        }
        return null;
    }
    
}
