import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ContratoComponentsPage, ContratoDeleteDialog, ContratoUpdatePage } from './contrato.page-object';

const expect = chai.expect;

describe('Contrato e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let contratoComponentsPage: ContratoComponentsPage;
  let contratoUpdatePage: ContratoUpdatePage;
  let contratoDeleteDialog: ContratoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Contratoes', async () => {
    await navBarPage.goToEntity('contrato');
    contratoComponentsPage = new ContratoComponentsPage();
    await browser.wait(ec.visibilityOf(contratoComponentsPage.title), 5000);
    expect(await contratoComponentsPage.getTitle()).to.eq('libraryApp.contrato.home.title');
    await browser.wait(ec.or(ec.visibilityOf(contratoComponentsPage.entities), ec.visibilityOf(contratoComponentsPage.noResult)), 1000);
  });

  it('should load create Contrato page', async () => {
    await contratoComponentsPage.clickOnCreateButton();
    contratoUpdatePage = new ContratoUpdatePage();
    expect(await contratoUpdatePage.getPageTitle()).to.eq('libraryApp.contrato.home.createOrEditLabel');
    await contratoUpdatePage.cancel();
  });

  it('should create and save Contratoes', async () => {
    const nbButtonsBeforeCreate = await contratoComponentsPage.countDeleteButtons();

    await contratoComponentsPage.clickOnCreateButton();

    await promise.all([
      contratoUpdatePage.setCodigoClienteSapInput('codigoClienteSap'),
      contratoUpdatePage.setNumeroContratoSapInput('numeroContratoSap'),
      contratoUpdatePage.setNumeroContratoTpzInput('numeroContratoTpz'),
      contratoUpdatePage.setNomeInput('nome'),
      contratoUpdatePage.setStatusInput('status'),
      contratoUpdatePage.setDataAssinaturaInput('2000-12-31'),
      contratoUpdatePage.setDataReajusteInput('2000-12-31'),
      contratoUpdatePage.setDataTerminoInput('2000-12-31'),
      contratoUpdatePage.setEstadoInput('estado'),
      contratoUpdatePage.setCnpjInput('cnpj'),
      contratoUpdatePage.setInscricaoEstadualInput('inscricaoEstadual'),
      contratoUpdatePage.setVigenciaInput('5'),
      contratoUpdatePage.notaFiscalSelectLastOption(),
      contratoUpdatePage.circuitoSelectLastOption()
    ]);

    expect(await contratoUpdatePage.getCodigoClienteSapInput()).to.eq(
      'codigoClienteSap',
      'Expected CodigoClienteSap value to be equals to codigoClienteSap'
    );
    expect(await contratoUpdatePage.getNumeroContratoSapInput()).to.eq(
      'numeroContratoSap',
      'Expected NumeroContratoSap value to be equals to numeroContratoSap'
    );
    expect(await contratoUpdatePage.getNumeroContratoTpzInput()).to.eq(
      'numeroContratoTpz',
      'Expected NumeroContratoTpz value to be equals to numeroContratoTpz'
    );
    expect(await contratoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await contratoUpdatePage.getStatusInput()).to.eq('status', 'Expected Status value to be equals to status');
    expect(await contratoUpdatePage.getDataAssinaturaInput()).to.eq(
      '2000-12-31',
      'Expected dataAssinatura value to be equals to 2000-12-31'
    );
    expect(await contratoUpdatePage.getDataReajusteInput()).to.eq('2000-12-31', 'Expected dataReajuste value to be equals to 2000-12-31');
    expect(await contratoUpdatePage.getDataTerminoInput()).to.eq('2000-12-31', 'Expected dataTermino value to be equals to 2000-12-31');
    expect(await contratoUpdatePage.getEstadoInput()).to.eq('estado', 'Expected Estado value to be equals to estado');
    expect(await contratoUpdatePage.getCnpjInput()).to.eq('cnpj', 'Expected Cnpj value to be equals to cnpj');
    expect(await contratoUpdatePage.getInscricaoEstadualInput()).to.eq(
      'inscricaoEstadual',
      'Expected InscricaoEstadual value to be equals to inscricaoEstadual'
    );
    expect(await contratoUpdatePage.getVigenciaInput()).to.eq('5', 'Expected vigencia value to be equals to 5');

    await contratoUpdatePage.save();
    expect(await contratoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await contratoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Contrato', async () => {
    const nbButtonsBeforeDelete = await contratoComponentsPage.countDeleteButtons();
    await contratoComponentsPage.clickOnLastDeleteButton();

    contratoDeleteDialog = new ContratoDeleteDialog();
    expect(await contratoDeleteDialog.getDialogTitle()).to.eq('libraryApp.contrato.delete.question');
    await contratoDeleteDialog.clickOnConfirmButton();

    expect(await contratoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
