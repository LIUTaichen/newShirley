import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { MatTableDataSource, MatSort, MatDialog } from '@angular/material';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { Niggle } from '../../../entities/niggle/niggle.model';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Principal } from '../../../shared';
import { NiggleRow } from '../../niggle-dw/niggle-list-dw/niggle-row.model';
import { NiggleService } from '../../../entities/niggle/niggle.service';
import { CreateDialogQuattraComponent } from './create-dialog-quattra/create-dialog-quattra.component';
import { EditDialogQuattraComponent } from './edit-dialog-quattra/edit-dialog-quattra.component';
import { DeleteDialogQuattraComponent } from './delete-dialog-quattra/delete-dialog-quattra.component';
import { NiggleUtilService } from '../../niggle-util.service';

@Component({
  selector: 'jhi-niggle-list-quattra',
  templateUrl: './niggle-list-quattra.component.html',
  styleUrls: ['./niggle-list-quattra.component.css']
})
export class NiggleListQuattraComponent implements OnInit, OnDestroy {

  niggles: Niggle[];
  idOfFocusedRow;
  filter: string;
  displayedColumns = ['priorityOrder', 'plantNumber', 'orderNo', 'quattraReference', 'plantDescription', 'location', 'locationUpdateTime',
    'description',
    'status',
    'quattraComments',
    'dateOpened',
    'daysOpened',
    // 'delete'
  ];
  dataSource: MatTableDataSource<NiggleRow>;
  @ViewChild(MatSort) sort: MatSort;

  currentAccount: any;
  eventSubscriber: Subscription;
  selectedOption = 'ALL';
  niggleRows: NiggleRow[] = new Array<NiggleRow>();
  whiteRows: NiggleRow[] = new Array<NiggleRow>();
  yellowRows: NiggleRow[] = new Array<NiggleRow>();
  completedRows: NiggleRow[] = new Array<NiggleRow>();
  // allowedStatus: Status[] = [Status.OPEN, Status.ON_HOLD, Status.IN_PROGRESS, Status.COMPLETED];
  allowedStatus: string[] = ['OPEN', 'ON_HOLD', 'IN_PROGRESS', 'COMPLETED'];
  queryParams: any = {
    'status.in': this.allowedStatus
  };

  constructor(
    private niggleService: NiggleService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal,
    public dialog: MatDialog,
  ) { }

  loadAll() {
    this.niggleService.query(this.queryParams).subscribe(
      (res: HttpResponse<Niggle[]>) => {
        this.niggles = res.body;
        const authorisedNiggles = this.niggles.filter((niggle) => this.isAuthorised(niggle)
          , this);

        this.niggleRows = authorisedNiggles.map(NiggleUtilService.convertEntityToRow, this);
        this.whiteRows = authorisedNiggles.filter((niggle) => this.isWhite(niggle)).map(NiggleUtilService.convertEntityToRow);
        this.yellowRows = authorisedNiggles.filter((niggle) => this.isYellow(niggle)).map(NiggleUtilService.convertEntityToRow);
        this.completedRows = authorisedNiggles.filter((niggle) => this.isCompleted(niggle)).map(NiggleUtilService.convertEntityToRow);
        this.updateDataSource();
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }
  ngOnInit() {
    this.loadAll();
    this.principal.identity().then((account) => {
      this.currentAccount = account;
    });
    this.registerChangeInNiggles();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: Niggle) {
    return item.id;
  }
  registerChangeInNiggles() {
    this.eventSubscriber = this.eventManager.subscribe('niggleListModification', (response) => this.loadAll());
  }

  private onError(error) {
    this.jhiAlertService.error(error.message, null, null);
  }

  applyFilter() {
    if (this.dataSource) {
      let filterValue = this.filter;
      if (filterValue) {
        filterValue = filterValue.trim(); // Remove whitespace
        filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
        this.dataSource.filter = filterValue;
      }
    }
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(CreateDialogQuattraComponent, {
      width: '500px',
      panelClass: 'niggle-panel',
      data: { name: 'this.name', animal: 'this.animal' }
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
      this.idOfFocusedRow = '';
      // this.animal = result;
    });
  }

  openDeleteDialog(id: number): void {
    this.idOfFocusedRow = id;
    const dialogRef = this.dialog.open(DeleteDialogQuattraComponent, {
      panelClass: 'niggle-delete-panel',
      data: { id }
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
      this.idOfFocusedRow = '';
    });
  }

  openEditDialog(id: number): void {
    this.idOfFocusedRow = id;
    const niggle: Niggle = this.niggles.find((niggleElement) => niggleElement.id === id);
    const dialogRef = this.dialog.open(EditDialogQuattraComponent, {
      width: '500px',
      panelClass: 'niggle-panel',
      data: { niggle, niggleRow: NiggleUtilService.convertEntityToRow(niggle), }
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
    });
  }

  updateDataSource() {
    let rowsToShow: NiggleRow[];
    switch (this.selectedOption) {
      case 'ALL': {
        rowsToShow = this.niggleRows;
        break;
      }
      case 'WHITE': {
        rowsToShow = this.whiteRows;
        break;
      }
      case 'YELLOW': {
        rowsToShow = this.yellowRows;
        break;
      }
      case 'COMPLETED': {
        rowsToShow = this.completedRows;
        break;
      }
    }
    this.dataSource = new MatTableDataSource(rowsToShow);
    this.dataSource.sort = this.sort;
    this.applyFilter();
  }

  isWhite(niggle: Niggle): Boolean {
    if (this.isCompleted(niggle)) {
      return false;
    } else if (niggle.plant && niggle.plant['category'] && niggle.plant['category'].maintenanceGroup.toString() === 'WHITE_FLEET') {
      return true;
    }
  }
  isYellow(niggle: Niggle): Boolean {
    if (this.isCompleted(niggle)) {
      return false;
    } else if (niggle.plant && niggle.plant['category'] && niggle.plant['category'].maintenanceGroup.toString() === 'YELLOW_FLEET') {
      return true;
    }
  }
  isCompleted(niggle: Niggle): Boolean {
    if (niggle.status.toString() === 'COMPLETED') {
      return true;
    }
    return false;
  }

  isAuthorised(niggle: Niggle): Boolean {
    if (!niggle.assignedContractor) {
      return false;
    }
    if (niggle.assignedContractor['name'] !== 'Quattra') {
      return false;
    } else if (this.allowedStatus.indexOf(niggle.status.toString()) === -1) {
      return false;
    } else if (!niggle.priority) {
      return false;
    } else {
      return true;
    }
  }

  getCount(): number {
    if (this.dataSource) {
      return this.dataSource.filteredData.length;
    } else {
      return 0;
    }
  }

}
