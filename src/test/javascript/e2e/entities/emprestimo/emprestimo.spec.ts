import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EmprestimoComponentsPage, EmprestimoDeleteDialog, EmprestimoUpdatePage } from './emprestimo.page-object';

const expect = chai.expect;

describe('Emprestimo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let emprestimoComponentsPage: EmprestimoComponentsPage;
  let emprestimoUpdatePage: EmprestimoUpdatePage;
  let emprestimoDeleteDialog: EmprestimoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Emprestimos', async () => {
    await navBarPage.goToEntity('emprestimo');
    emprestimoComponentsPage = new EmprestimoComponentsPage();
    await browser.wait(ec.visibilityOf(emprestimoComponentsPage.title), 5000);
    expect(await emprestimoComponentsPage.getTitle()).to.eq('libraryApp.emprestimo.home.title');
  });

  it('should load create Emprestimo page', async () => {
    await emprestimoComponentsPage.clickOnCreateButton();
    emprestimoUpdatePage = new EmprestimoUpdatePage();
    expect(await emprestimoUpdatePage.getPageTitle()).to.eq('libraryApp.emprestimo.home.createOrEditLabel');
    await emprestimoUpdatePage.cancel();
  });

  it('should create and save Emprestimos', async () => {
    const nbButtonsBeforeCreate = await emprestimoComponentsPage.countDeleteButtons();

    await emprestimoComponentsPage.clickOnCreateButton();
    await promise.all([
      emprestimoUpdatePage.setDataEmprestimoInput('2000-12-31'),
      emprestimoUpdatePage.livroSelectLastOption(),
      emprestimoUpdatePage.alunoSelectLastOption()
    ]);
    expect(await emprestimoUpdatePage.getDataEmprestimoInput()).to.eq(
      '2000-12-31',
      'Expected dataEmprestimo value to be equals to 2000-12-31'
    );
    await emprestimoUpdatePage.save();
    expect(await emprestimoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await emprestimoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Emprestimo', async () => {
    const nbButtonsBeforeDelete = await emprestimoComponentsPage.countDeleteButtons();
    await emprestimoComponentsPage.clickOnLastDeleteButton();

    emprestimoDeleteDialog = new EmprestimoDeleteDialog();
    expect(await emprestimoDeleteDialog.getDialogTitle()).to.eq('libraryApp.emprestimo.delete.question');
    await emprestimoDeleteDialog.clickOnConfirmButton();

    expect(await emprestimoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
