import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EmpresaComponentsPage, EmpresaDeleteDialog, EmpresaUpdatePage } from './empresa.page-object';

const expect = chai.expect;

describe('Empresa e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let empresaComponentsPage: EmpresaComponentsPage;
  let empresaUpdatePage: EmpresaUpdatePage;
  let empresaDeleteDialog: EmpresaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Empresas', async () => {
    await navBarPage.goToEntity('empresa');
    empresaComponentsPage = new EmpresaComponentsPage();
    await browser.wait(ec.visibilityOf(empresaComponentsPage.title), 5000);
    expect(await empresaComponentsPage.getTitle()).to.eq('libraryApp.empresa.home.title');
    await browser.wait(ec.or(ec.visibilityOf(empresaComponentsPage.entities), ec.visibilityOf(empresaComponentsPage.noResult)), 1000);
  });

  it('should load create Empresa page', async () => {
    await empresaComponentsPage.clickOnCreateButton();
    empresaUpdatePage = new EmpresaUpdatePage();
    expect(await empresaUpdatePage.getPageTitle()).to.eq('libraryApp.empresa.home.createOrEditLabel');
    await empresaUpdatePage.cancel();
  });

  it('should create and save Empresas', async () => {
    const nbButtonsBeforeCreate = await empresaComponentsPage.countDeleteButtons();

    await empresaComponentsPage.clickOnCreateButton();

    await promise.all([empresaUpdatePage.setNomeInput('nome')]);

    expect(await empresaUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');

    await empresaUpdatePage.save();
    expect(await empresaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await empresaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Empresa', async () => {
    const nbButtonsBeforeDelete = await empresaComponentsPage.countDeleteButtons();
    await empresaComponentsPage.clickOnLastDeleteButton();

    empresaDeleteDialog = new EmpresaDeleteDialog();
    expect(await empresaDeleteDialog.getDialogTitle()).to.eq('libraryApp.empresa.delete.question');
    await empresaDeleteDialog.clickOnConfirmButton();

    expect(await empresaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
