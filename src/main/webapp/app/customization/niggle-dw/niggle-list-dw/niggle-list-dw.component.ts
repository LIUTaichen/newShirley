import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { MatTableDataSource, MatSort, MatDialog } from '@angular/material';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import 'rxjs/add/operator/debounceTime';
import { Niggle } from '../../../entities/niggle/niggle.model';
import { Plant } from '../../../entities/plant/plant.model';
import { NiggleRow } from './niggle-row.model';
import { NiggleService } from '../../../entities/niggle/niggle.service';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Principal } from '../../../shared';
import { NiggleCreateDialogComponent } from './niggle-create-dialog/niggle-create-dialog.component';
import { NiggleEditDialogComponent } from './niggle-edit-dialog/niggle-edit-dialog.component';
import { PlantService } from '../../../entities/plant';
import { MaintenanceContractor, MaintenanceContractorService } from '../../../entities/maintenance-contractor';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { NiggleUtilService } from '../../niggle-util.service';

@Component({
  selector: 'jhi-niggle-list-dw',
  templateUrl: './niggle-list-dw.component.html',
  styleUrls: ['./niggle-list-dw.component.css']
})

export class NiggleListDwComponent implements OnInit, OnDestroy {
  readonly niggleListLocalStorageKey = 'niggle.list.dw';
  readonly plantListLocalStorageKey = 'plant.list.dw';
  readonly filterFormValueLocalStorageKey = 'filter.dw';
  niggles: Niggle[];
  plants: Plant[];
  filterForm: FormGroup;
  filter = '';
  maintenanceContractors: MaintenanceContractor[];
  idOfFocusedRow;
  displayedColumns = [
    'plantNumber',
    'orderNo',
    'quattraReference',
    'plantDescription',
    'location',
    'description',
    'status',
    'contractor',
    'quattraComments',
    'dateOpened',
    'daysOpened',
    'priorityOrder',
    'lastModifiedBy',
    'lastModifiedDate'
  ];
  dataSource: MatTableDataSource<NiggleRow>;

  @ViewChild(MatSort) sort: MatSort;

  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private niggleService: NiggleService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal,
    public dialog: MatDialog,
    private plantService: PlantService,
    private maintenanceContractorService: MaintenanceContractorService,
    private fb: FormBuilder,
  ) {
  }

  loadAll() {
    this.loadNiggles();
    this.plantService.query()
      .subscribe((res: HttpResponse<Plant[]>) => {
        const plants = res.body;
        this.plants = plants.sort((a: Plant, b: Plant) => {
          if (a.fleetId < b.fleetId) {
            return -1;
          } else if (a.fleetId > b.fleetId) {
            return 1;
          } else {
            return 0;
          }
        });
        localStorage.setItem(this.plantListLocalStorageKey, JSON.stringify(this.plants));
      }, (res: HttpErrorResponse) => this.onError(res.message));
    this.maintenanceContractorService.query()
      .subscribe((res: HttpResponse<MaintenanceContractor[]>) => {
        this.maintenanceContractors = res.body;
      }, (res: HttpErrorResponse) => this.onError(res.message));
  }

  loadNiggles() {
    this.niggleService.query().subscribe(
      (res: HttpResponse<Niggle[]>) => {
        this.niggles = res.body;
        this.prepareDataDource();
        this.applyFilter();
        localStorage.setItem(this.niggleListLocalStorageKey, JSON.stringify(this.niggles));
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  prepareDataDource() {
    if (this.niggles) {
      this.dataSource = new MatTableDataSource(this.niggles.filter((niggle) => this.needToShow(niggle), this).map((niggle) => NiggleUtilService.convertEntityToRow(niggle), this));
      this.dataSource.sort = this.sort;
    }
  }

  ngOnInit() {
    let localFilter = JSON.parse(localStorage.getItem(this.filterFormValueLocalStorageKey), this.dateReviver);
    this.niggles = JSON.parse(localStorage.getItem(this.niggleListLocalStorageKey), this.dateReviver);
    this.plants = JSON.parse(localStorage.getItem(this.plantListLocalStorageKey), this.dateReviver);
    if (!localFilter) {
      localFilter = {
        owner: 'DEMPSEY',
        status: ['OPEN', 'IN_PROGRESS', 'ON_HOLD']
      };
      localStorage.setItem(this.filterFormValueLocalStorageKey, JSON.stringify(localFilter));
    }
    this.filterForm = this.fb.group({
      owner: localFilter['owner'],
      status: new FormControl(localFilter.status)
    });

    this.prepareDataDource();
    this.loadAll();
    this.principal.identity().then((account) => {
      this.currentAccount = account;
    });
    this.registerChangeInNiggles();
    this.filterForm.valueChanges
      .debounceTime(500)
      .subscribe((value) => {
        localStorage.setItem(this.filterFormValueLocalStorageKey, JSON.stringify(value));
        this.prepareDataDource();
      });
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: Niggle) {
    return item.id;
  }
  registerChangeInNiggles() {
    this.eventSubscriber = this.eventManager.subscribe('niggleListModification', (response) => this.loadNiggles());
  }

  private onError(error) {
    this.jhiAlertService.error(error.message, null, null);
  }

  applyFilter() {
    if (this.dataSource) {
      let filterValue = this.filter;
      filterValue = filterValue.trim(); // Remove whitespace
      filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
      this.dataSource.filter = filterValue;
    }
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(NiggleCreateDialogComponent, {
      width: '500px',
      panelClass: 'niggle-panel',
      data: {
        plants: this.plants,
        maintenanceContractors: this.maintenanceContractors
      }
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
      this.idOfFocusedRow = '';
      // this.animal = result;
    });
  }

  openEditDialog(id: number): void {
    this.idOfFocusedRow = id;
    const niggle: Niggle = this.niggles.find((niggleElement) => niggleElement.id === id);
    const dialogRef = this.dialog.open(NiggleEditDialogComponent, {
      width: '500px',
      panelClass: 'niggle-panel',
      data: {
        niggle,
        plants: this.plants,
        row: NiggleUtilService.convertEntityToRow(niggle),
        maintenanceContractors: this.maintenanceContractors
      }
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
      // this.animal = result;
    });
  }

  getCount(): number {
    if (this.dataSource) {
      return this.dataSource.filteredData.length;
    } else {
      return 0;
    }
  }

  needToShow(niggle: Niggle): Boolean {
    const needToShowNiggleBasedOnOwner = this.needToShowNiggleBasedOnOwner(niggle);
    const needToShowNiggleBasedOnStatus = this.needToShowNiggleBasedOnStatus(niggle);
    return needToShowNiggleBasedOnOwner && needToShowNiggleBasedOnStatus;
  }

  needToShowNiggleBasedOnOwner(niggle: Niggle): Boolean {
    const ownerOption = this.filterForm.value.owner;
    if (ownerOption !== 'ALL') {
      if (ownerOption === 'DEMPSEY') {
        if (niggle.plant['owner']['company'] !== 'Dempsey Wood Civil') {
          return false;
        }
      } else {
        if (niggle.plant['owner']['company'] === 'Dempsey Wood Civil') {
          return false;
        }
      }
    }
    return true;
  }

  needToShowNiggleBasedOnStatus(niggle: Niggle): Boolean {
    const statusOption = this.filterForm.value.status;
    if (!statusOption || statusOption.length === 0) {
      return false;
    }
    if (statusOption.includes(niggle.status.toString())) {
      return true;
    }
    return false;
  }

  dateReviver(key, value) {
    let a;
    if (typeof value === 'string') {
      a = /^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2}(?:\.\d*)?)Z$/.exec(value);
      if (a) {
        return new Date(Date.UTC(+a[1], +a[2] - 1, +a[3], +a[4],
          +a[5], +a[6]));
      }
    }
    return value;
  }
}
