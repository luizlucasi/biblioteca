import { element, by, ElementFinder } from 'protractor';

export class EmprestimoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-emprestimo div table .btn-danger'));
  title = element.all(by.css('jhi-emprestimo div h2#page-heading span')).first();

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class EmprestimoUpdatePage {
  pageTitle = element(by.id('jhi-emprestimo-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  dataEmprestimoInput = element(by.id('field_dataEmprestimo'));
  livroSelect = element(by.id('field_livro'));
  alunoSelect = element(by.id('field_aluno'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDataEmprestimoInput(dataEmprestimo: string): Promise<void> {
    await this.dataEmprestimoInput.sendKeys(dataEmprestimo);
  }

  async getDataEmprestimoInput(): Promise<string> {
    return await this.dataEmprestimoInput.getAttribute('value');
  }

  async livroSelectLastOption(): Promise<void> {
    await this.livroSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async livroSelectOption(option: string): Promise<void> {
    await this.livroSelect.sendKeys(option);
  }

  getLivroSelect(): ElementFinder {
    return this.livroSelect;
  }

  async getLivroSelectedOption(): Promise<string> {
    return await this.livroSelect.element(by.css('option:checked')).getText();
  }

  async alunoSelectLastOption(): Promise<void> {
    await this.alunoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async alunoSelectOption(option: string): Promise<void> {
    await this.alunoSelect.sendKeys(option);
  }

  getAlunoSelect(): ElementFinder {
    return this.alunoSelect;
  }

  async getAlunoSelectedOption(): Promise<string> {
    return await this.alunoSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class EmprestimoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-emprestimo-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-emprestimo'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
