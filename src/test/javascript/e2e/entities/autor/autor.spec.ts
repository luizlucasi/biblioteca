import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AutorComponentsPage, AutorDeleteDialog, AutorUpdatePage } from './autor.page-object';

const expect = chai.expect;

describe('Autor e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let autorComponentsPage: AutorComponentsPage;
  let autorUpdatePage: AutorUpdatePage;
  let autorDeleteDialog: AutorDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Autors', async () => {
    await navBarPage.goToEntity('autor');
    autorComponentsPage = new AutorComponentsPage();
    await browser.wait(ec.visibilityOf(autorComponentsPage.title), 5000);
    expect(await autorComponentsPage.getTitle()).to.eq('libraryApp.autor.home.title');
  });

  it('should load create Autor page', async () => {
    await autorComponentsPage.clickOnCreateButton();
    autorUpdatePage = new AutorUpdatePage();
    expect(await autorUpdatePage.getPageTitle()).to.eq('libraryApp.autor.home.createOrEditLabel');
    await autorUpdatePage.cancel();
  });

  it('should create and save Autors', async () => {
    const nbButtonsBeforeCreate = await autorComponentsPage.countDeleteButtons();

    await autorComponentsPage.clickOnCreateButton();
    await promise.all([autorUpdatePage.setNomeInput('nome'), autorUpdatePage.setSobrenomeInput('sobrenome')]);
    expect(await autorUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await autorUpdatePage.getSobrenomeInput()).to.eq('sobrenome', 'Expected Sobrenome value to be equals to sobrenome');
    await autorUpdatePage.save();
    expect(await autorUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await autorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Autor', async () => {
    const nbButtonsBeforeDelete = await autorComponentsPage.countDeleteButtons();
    await autorComponentsPage.clickOnLastDeleteButton();

    autorDeleteDialog = new AutorDeleteDialog();
    expect(await autorDeleteDialog.getDialogTitle()).to.eq('libraryApp.autor.delete.question');
    await autorDeleteDialog.clickOnConfirmButton();

    expect(await autorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
