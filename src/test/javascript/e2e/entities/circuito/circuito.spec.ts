import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CircuitoComponentsPage, CircuitoDeleteDialog, CircuitoUpdatePage } from './circuito.page-object';

const expect = chai.expect;

describe('Circuito e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let circuitoComponentsPage: CircuitoComponentsPage;
  let circuitoUpdatePage: CircuitoUpdatePage;
  let circuitoDeleteDialog: CircuitoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Circuitos', async () => {
    await navBarPage.goToEntity('circuito');
    circuitoComponentsPage = new CircuitoComponentsPage();
    await browser.wait(ec.visibilityOf(circuitoComponentsPage.title), 5000);
    expect(await circuitoComponentsPage.getTitle()).to.eq('libraryApp.circuito.home.title');
    await browser.wait(ec.or(ec.visibilityOf(circuitoComponentsPage.entities), ec.visibilityOf(circuitoComponentsPage.noResult)), 1000);
  });

  it('should load create Circuito page', async () => {
    await circuitoComponentsPage.clickOnCreateButton();
    circuitoUpdatePage = new CircuitoUpdatePage();
    expect(await circuitoUpdatePage.getPageTitle()).to.eq('libraryApp.circuito.home.createOrEditLabel');
    await circuitoUpdatePage.cancel();
  });

  it('should create and save Circuitos', async () => {
    const nbButtonsBeforeCreate = await circuitoComponentsPage.countDeleteButtons();

    await circuitoComponentsPage.clickOnCreateButton();

    await promise.all([
      circuitoUpdatePage.setIdControleDesignacaoInput('idControleDesignacao'),
      circuitoUpdatePage.setIdEstacaoInput('idEstacao')
    ]);

    expect(await circuitoUpdatePage.getIdControleDesignacaoInput()).to.eq(
      'idControleDesignacao',
      'Expected IdControleDesignacao value to be equals to idControleDesignacao'
    );
    expect(await circuitoUpdatePage.getIdEstacaoInput()).to.eq('idEstacao', 'Expected IdEstacao value to be equals to idEstacao');

    await circuitoUpdatePage.save();
    expect(await circuitoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await circuitoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Circuito', async () => {
    const nbButtonsBeforeDelete = await circuitoComponentsPage.countDeleteButtons();
    await circuitoComponentsPage.clickOnLastDeleteButton();

    circuitoDeleteDialog = new CircuitoDeleteDialog();
    expect(await circuitoDeleteDialog.getDialogTitle()).to.eq('libraryApp.circuito.delete.question');
    await circuitoDeleteDialog.clickOnConfirmButton();

    expect(await circuitoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
