package com.treasure.parttimejob.mvp.presenter;

import com.treasure.parttimejob.mvp.model.LoginModel;
import com.treasure.parttimejob.mvp.model.OnLoginListener;
import com.treasure.parttimejob.mvp.model.UserInfo;
import com.treasure.parttimejob.mvp.view.ILoginView;
import com.treasure.parttimejob.mvp.view.IRegisterView;

/**
 * ============================================================
 * Author：   treasure
 * time：  2019/2/27
 * description:
 * ============================================================
 */
public class LoginPresenter {
  private LoginModel loginModel;
  private ILoginView iLoginView;
  private IRegisterView iRegisterView;

  public LoginPresenter(ILoginView iLoginView) {
    this.iLoginView = iLoginView;
    this.loginModel = new LoginModel();
  }

  public LoginPresenter(IRegisterView iRegisterView) {
    this.iRegisterView = iRegisterView;
    this.loginModel = new LoginModel();
  }

  /**
   * 登录操作的调度
   */
  public void login() {
    if (iLoginView == null) return;
    loginModel.login(iLoginView.getUserName(), iLoginView.getUserPwd(), new OnLoginListener() {
      @Override
      public void success() {
        iLoginView.showLoginSuccess();
      }

      @Override
      public void failed(String errorMsg) {
        iLoginView.showLoginFail(errorMsg);
      }
    });
  }

  /**
   * 注册操作的调度
   */
  public void register() {
    if (iRegisterView == null) return;
    loginModel.register(iRegisterView.getUserName(), iRegisterView.getUserPwd(), iRegisterView.getUserIntro(),iRegisterView.getUserIdentity(), new OnLoginListener() {
      @Override
      public void success() {
        iRegisterView.showSuccess();
      }

      @Override
      public void failed(String errorMsg) {
        iRegisterView.showFailMsg(errorMsg);
      }
    });
  }
}
