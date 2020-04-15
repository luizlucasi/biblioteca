import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { NotaFiscalComponentsPage, NotaFiscalDeleteDialog, NotaFiscalUpdatePage } from './nota-fiscal.page-object';

const expect = chai.expect;

describe('NotaFiscal e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let notaFiscalComponentsPage: NotaFiscalComponentsPage;
  let notaFiscalUpdatePage: NotaFiscalUpdatePage;
  let notaFiscalDeleteDialog: NotaFiscalDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load NotaFiscals', async () => {
    await navBarPage.goToEntity('nota-fiscal');
    notaFiscalComponentsPage = new NotaFiscalComponentsPage();
    await browser.wait(ec.visibilityOf(notaFiscalComponentsPage.title), 5000);
    expect(await notaFiscalComponentsPage.getTitle()).to.eq('libraryApp.notaFiscal.home.title');
    await browser.wait(ec.or(ec.visibilityOf(notaFiscalComponentsPage.entities), ec.visibilityOf(notaFiscalComponentsPage.noResult)), 1000);
  });

  it('should load create NotaFiscal page', async () => {
    await notaFiscalComponentsPage.clickOnCreateButton();
    notaFiscalUpdatePage = new NotaFiscalUpdatePage();
    expect(await notaFiscalUpdatePage.getPageTitle()).to.eq('libraryApp.notaFiscal.home.createOrEditLabel');
    await notaFiscalUpdatePage.cancel();
  });

  it('should create and save NotaFiscals', async () => {
    const nbButtonsBeforeCreate = await notaFiscalComponentsPage.countDeleteButtons();

    await notaFiscalComponentsPage.clickOnCreateButton();

    await promise.all([
      notaFiscalUpdatePage.setNumeroInput('numero'),
      notaFiscalUpdatePage.setCompetenciaInput('competencia'),
      notaFiscalUpdatePage.setTipoInput('tipo'),
      notaFiscalUpdatePage.setServicoInput('servico'),
      notaFiscalUpdatePage.setCondicaoPagamentoInput('condicaoPagamento'),
      notaFiscalUpdatePage.setTotalNFInput('5')
    ]);

    expect(await notaFiscalUpdatePage.getNumeroInput()).to.eq('numero', 'Expected Numero value to be equals to numero');
    expect(await notaFiscalUpdatePage.getCompetenciaInput()).to.eq('competencia', 'Expected Competencia value to be equals to competencia');
    expect(await notaFiscalUpdatePage.getTipoInput()).to.eq('tipo', 'Expected Tipo value to be equals to tipo');
    expect(await notaFiscalUpdatePage.getServicoInput()).to.eq('servico', 'Expected Servico value to be equals to servico');
    expect(await notaFiscalUpdatePage.getCondicaoPagamentoInput()).to.eq(
      'condicaoPagamento',
      'Expected CondicaoPagamento value to be equals to condicaoPagamento'
    );
    expect(await notaFiscalUpdatePage.getTotalNFInput()).to.eq('5', 'Expected totalNF value to be equals to 5');

    await notaFiscalUpdatePage.save();
    expect(await notaFiscalUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await notaFiscalComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last NotaFiscal', async () => {
    const nbButtonsBeforeDelete = await notaFiscalComponentsPage.countDeleteButtons();
    await notaFiscalComponentsPage.clickOnLastDeleteButton();

    notaFiscalDeleteDialog = new NotaFiscalDeleteDialog();
    expect(await notaFiscalDeleteDialog.getDialogTitle()).to.eq('libraryApp.notaFiscal.delete.question');
    await notaFiscalDeleteDialog.clickOnConfirmButton();

    expect(await notaFiscalComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
