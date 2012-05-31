package na.services.myNutritionalProfile.ui.question;

import java.util.Comparator;

/*
 * Compares Field 1 from both arrays
 */
@SuppressWarnings("unchecked")
public class AnswerComparator implements Comparator
{
  public int compare(Object obj1, Object obj2)
  {
    int result = 0;
 
    na.miniDao.Answer ans1 = (na.miniDao.Answer) obj1;
    na.miniDao.Answer ans2 = (na.miniDao.Answer) obj2;
 
    /* Sort on first element of each array (last name) */
    result = new Integer(ans1.getOrder()).compareTo(ans2.getOrder());
    return result;
  }
}