package com.treasure.parttimejob.db.utils;

import android.content.Context;

import com.treasure.parttimejob.PJApp;
import com.treasure.parttimejob.db.greendao.DaoSession;
import com.treasure.parttimejob.db.greendao.MsgBeanDao;
import com.treasure.parttimejob.db.greendao.PartTimeBeanDao;
import com.treasure.parttimejob.db.greendao.UserInfoDao;
import com.treasure.parttimejob.mvp.model.UserInfo;
import com.treasure.parttimejob.ui.model.MsgBean;
import com.treasure.parttimejob.ui.model.PartTimeBean;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

import static com.treasure.parttimejob.PJApp.userDaoManager;

/**
 * ============================================================
 * Author：   treasure
 * time：  2019/3/5
 * description:
 * ============================================================
 */
public class UserDaoManager {
  private static UserDaoManager daoManager;
  private static Context appContext;
  private DaoSession daoSession;
  private UserInfoDao userInfoDao;
  private PartTimeBeanDao partTimeBeanDao;
  private MsgBeanDao msgBeanDao;

  public UserDaoManager() {
  }

  public static UserDaoManager getInstance(Context context) {
    if (daoManager == null) {
      synchronized (UserDaoManager.class) {
        if (daoManager == null) {
          daoManager = new UserDaoManager();
          if (appContext == null) {
            appContext = context.getApplicationContext();
          }
          daoManager.daoSession = PJApp.getDaoSession(context);
          daoManager.userInfoDao = daoManager.daoSession.getUserInfoDao();
          daoManager.partTimeBeanDao = daoManager.daoSession.getPartTimeBeanDao();
          daoManager.msgBeanDao = daoManager.daoSession.getMsgBeanDao();
        }
      }
    }
    return daoManager;
  }

  public List<UserInfo> OrderAscPerson() {
    return userInfoDao.queryBuilder().orderAsc(UserInfoDao.Properties.Id).list();
  }

  /**
   * 增
   *
   * @param userInfo
   */
  public void insertUser(UserInfo userInfo) {
    userInfoDao.insert(userInfo);
  }

  /**
   * 删
   *
   * @param userInfo
   */
  public void deleteUser(UserInfo userInfo) {
    userInfoDao.delete(userInfo);
  }

  /**
   * 查
   *
   * @param arg0
   * @param conditions
   * @return
   */
  public List<UserInfo> queryUser(WhereCondition arg0, WhereCondition... conditions) {
    QueryBuilder<UserInfo> queryBuilder = userInfoDao.queryBuilder();
    queryBuilder.where(arg0, conditions);
    return queryBuilder.list();
  }

  /**
   * 改
   *
   * @param userInfo
   */
  public void updateUser(UserInfo userInfo) {
    userInfoDao.update(userInfo);
  }

  public void insertOrReplaceUser(UserInfo userInfo) {
    userInfoDao.insertOrReplaceInTx(userInfo);
  }

//  public static boolean checkPersonExistAndUpdate(String name) {
//    List<Person> personList = App.daoManager.queryPerson(PersonDao.Properties.Name.eq(name));
//    if (personList.size() > 0) {
//      for (int i = 0; i < personList.size(); i++) {
//        Person person = new Person(personList.get(i).getId(), personList.get(i).getName(), personList.get(i).getHigh(), personList.get(i).getAge());
//        App.daoManager.insertOrReplacePerson(person);
//      }
//      return true;
//    }
//    return false;
//
//  }

  /**
   * 对象是否存在
   */
  public static boolean checkPersonExist(String name) {
    List<UserInfo> userInfoList = PJApp.userDaoManager.queryUser(UserInfoDao.Properties.Name.eq(name));
    if (userInfoList.size() > 0) {
      return true;
    }
    return false;
  }

  /**
   * 获取用户密码
   *
   * @param name
   * @return
   */
  public static String getUserPwd(String name) {
    List<UserInfo> userInfos = PJApp.userDaoManager.queryUser(UserInfoDao.Properties.Name.eq(name));
    if (userInfos.size() > 0) {
      return userInfos.get(0).getPwd();
    }
    return "";
  }

  /**
   * 获取用户bean
   *
   * @param name
   * @return
   */
  public static String getUserInfo(String name) {
    List<UserInfo> userInfos = PJApp.userDaoManager.queryUser(UserInfoDao.Properties.Name.eq(name));
    if (userInfos.size() > 0) {
      return userInfos.get(0).getInfo();
    }
    return "";
  }

  /**
   * 获取用户数据体
   */
  public static UserInfo getUser(String name) {
    List<UserInfo> userInfos = PJApp.userDaoManager.queryUser(UserInfoDao.Properties.Name.eq(name));
    if (userInfos.size() > 0) {
      return userInfos.get(0);
    }
    return null;
  }

  /**
   * =============================================================================
   *
   *                          兼职详情
   *
   * =============================================================================
   */
  /**
   * 增
   *
   * @param partTimeBean
   */
  public void insertPart(PartTimeBean partTimeBean) {
    partTimeBeanDao.insert(partTimeBean);
  }

  /**
   * 查
   *
   * @param arg0
   * @param conditions
   * @return
   */
  public List<PartTimeBean> queryPart(WhereCondition arg0, WhereCondition... conditions) {
    QueryBuilder<PartTimeBean> queryBuilder = partTimeBeanDao.queryBuilder();
    queryBuilder.where(arg0, conditions);
    return queryBuilder.list();
  }

  /**
   * 获取全部的兼职列表
   *
   * @return
   */
  public List<PartTimeBean> queryAllPart() {
    Query<PartTimeBean> build = partTimeBeanDao.queryBuilder().build();
    return build.list();
  }

  /**
   * 获取长短期的兼职列表
   *
   * @return
   */
  public List<PartTimeBean> queryLongPart(String duration) {
    return PJApp.userDaoManager.queryPart(PartTimeBeanDao.Properties.Part_duration.eq(duration));
  }

  /**
   * 获取技能实习的兼职列表
   *
   * @return
   */
  public List<PartTimeBean> querySkillPart(String skill) {
    return PJApp.userDaoManager.queryPart(PartTimeBeanDao.Properties.Part_type.eq(skill));
  }

  /**
   * 根据ID获取兼职信息
   *
   * @return
   */
  public PartTimeBean queryIdPart(long id) {
    List<PartTimeBean> partTimeBeanList = PJApp.userDaoManager.queryPart(PartTimeBeanDao.Properties.Id.eq(id));
    if (partTimeBeanList != null && partTimeBeanList.size() > 0) {
      return partTimeBeanList.get(0);
    }
    return null;
  }
  /**
   * 根据申请情况获取兼职信息
   *
   * @return
   */
  public List<PartTimeBean> queryApplyPart(int type) {
    return PJApp.userDaoManager.queryPart(PartTimeBeanDao.Properties.Part_apply.eq(type));
  } /**
   * 根据已收藏获取兼职信息
   *
   * @return
   */
  public List<PartTimeBean> queryCollectPart() {
    return PJApp.userDaoManager.queryPart(PartTimeBeanDao.Properties.Part_collect.eq(true));
  }
  /**
   * 改
   *
   * @param partTimeBean
   */
  public void updatePart(PartTimeBean partTimeBean) {
    partTimeBeanDao.update(partTimeBean);
  }
  /**
   * 全部删除
   */
  public void deletePartAll() {
    partTimeBeanDao.deleteAll();
  }

  /**
   * =============================================================================
   *
   *                          通知消息列表
   *
   * =============================================================================
   */

  /**
   * 增
   *
   * @param msgBean
   */
  public void insertMsg(MsgBean msgBean) {
    msgBeanDao.insert(msgBean);
  }

  /**
   * 查
   *
   */
  public List<MsgBean> queryMsg(WhereCondition arg0, WhereCondition... conditions) {
    QueryBuilder<MsgBean> queryBuilder = msgBeanDao.queryBuilder();
    queryBuilder.where(arg0, conditions);
    return queryBuilder.list();
  }
  /**
   * 根据用户ID查找MSg列表
   */
  public List<MsgBean> queryIdMsg(String user_name) {
    return PJApp.userDaoManager.queryMsg(MsgBeanDao.Properties.User_name.eq(user_name));
  }
}
