package gurinovich.java;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class MyFilter extends FileFilter
{
    String ext;

    MyFilter(String ext)
    {
        this.ext = ext;
    }

    public boolean accept(File pathname)
    {
        String extension = getExtension(pathname);
        return extension.equals(ext);
    }

    private String getExtension(File pathname)
    {
        String filename = pathname.getPath();
        int i = filename.lastIndexOf('.');
        if ( i > 0 && i < filename.length() - 1 )
        {
            return filename.substring(i + 1).toLowerCase();
        }
        return "";
    }

    @Override
    public String getDescription()
    {
        return ext;
    }
}