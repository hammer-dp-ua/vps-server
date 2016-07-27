package ua.dp.hammer.vpsserver.controllers;

import org.junit.Assert;
import org.junit.Test;
import ua.dp.hammer.vpsserver.models.Picture;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class MultimediaFilesManagerControllerTest {

   @Test
   public void testImageFilesSorting() {
      SortedSet<Picture> imageFiles = new TreeSet<>(MultimediaFilesManagerController.IMAGE_FILES_COMPARATOR_ASC);

      imageFiles.add(new Picture("001.jpeg", null));
      imageFiles.add(new Picture("002.jpeg", null));
      imageFiles.add(new Picture("010.jpeg", null));
      imageFiles.add(new Picture("050.jpeg", null));
      imageFiles.add(new Picture("100.jpeg", null));

      Iterator<Picture> picturesIterator = imageFiles.iterator();

      Assert.assertEquals("001.jpeg", picturesIterator.next().getName());
      Assert.assertEquals("002.jpeg", picturesIterator.next().getName());
      Assert.assertEquals("010.jpeg", picturesIterator.next().getName());
      Assert.assertEquals("050.jpeg", picturesIterator.next().getName());
      Assert.assertEquals("100.jpeg", picturesIterator.next().getName());
   }
}
